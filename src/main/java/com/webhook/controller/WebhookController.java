package com.webhook.controller;

import com.webhook.service.CommonService;
import com.webhook.utils.ShellUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * webhook controller
 */
@RestController
@RequestMapping
public class WebhookController {
    /**
     * 脚本路径
     */
    @Value("${webhook.script.path}")
    private String scriptPath;

    /**
     * 脚本名列表
     */
    @Value("${webhook.script.names}")
    private List<String> scriptNameList;

    @Resource
    private CommonService commonService;

    /**
     * 执行脚本
     *
     * @param name   脚本名
     * @param secret webhook 密钥
     * @return java.lang.String 成功/失败
     */
    @GetMapping
    public String exec(@RequestParam String name, String secret) {
        String msg = "执行 " + name + ".sh ";

        // 校验 ip 白名单、webhook 密钥
        if (!commonService.check(secret, msg)) {
            return "失败";
        }
        // 脚本名非空
        if (StringUtils.isEmpty(name)) {
            System.out.println(msg + "失败，未输入脚本名");
            return "失败";
        }
        // 脚本名校验
        if (!scriptNameList.contains(name)) {
            System.out.println(msg + "失败，脚本名错误，脚本名为 " + name);
            return "失败";
        }

        return ShellUtils.exec(scriptPath, name, msg);
    }
}