package com.webhook.controller;

import com.webhook.utils.DateUtils;
import com.webhook.utils.HttpUtils;
import com.webhook.utils.ShellUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * webhook controller
 */
@RestController
@RequestMapping("webhook")
public class WebhookController {
    /**
     * 是否启用 IP 白名单（0：禁用；1：启用）
     */
    @Value("${ip.whitelist.enabled}")
    private Boolean ipEnabled;

    /**
     * IP 白名单
     */
    @Value("${ip.whitelist.content}")
    private List<String> ipList;

    /**
     * 是否启用 webhook 密钥（0：禁用；1：启用）
     */
    @Value("${webhook.secret.enabled}")
    private Boolean secretEnabled;

    /**
     * webhook 密钥，防止接口被盗用
     */
    @Value("${webhook.secret.content}")
    private String secret;

    /**
     * 脚本路径
     */
    @Value("${script.path}")
    private String scriptPath;

    /**
     * 脚本名列表
     */
    @Value("${script.names}")
    private List<String> scriptNameList;

    /**
     * 执行脚本
     *
     * @param name   脚本名
     * @param secret webhook 密钥
     * @return java.lang.String 成功/失败
     */
    @GetMapping
    public String exec(@RequestParam String name, String secret) {
        String ip = HttpUtils.getIpAddress();
        String date = DateUtils.dateToString(new Date());
        String msg = date + " IP：" + ip + " 执行 " + name + ".sh ";

        // 脚本名非空
        if (StringUtils.isEmpty(name)) {
            System.out.println(msg + "失败，未输入脚本名");
            return "失败";
        }
        // IP 白名单校验
        if (ipEnabled && !ipList.contains(ip)) {
            System.out.println(msg + "失败，IP 错误，IP 为 " + ip);
            return "失败";
        }
        // webhook 密钥校验
        if (secretEnabled && !this.secret.equals(secret)) {
            System.out.println(msg + "失败，密钥错误，密钥为 " + secret);
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