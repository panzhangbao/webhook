package com.webhook.controller;

import com.webhook.service.CommonService;
import com.webhook.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 文件 controller
 */
@RestController
@RequestMapping("file")
public class FileController {
    /**
     * 文件存储路径
     */
    @Value("${webhook.file.path}")
    private String filePath;

    @Resource
    private CommonService commonService;

    /**
     * 上传文件到本地
     *
     * @param file
     * @return java.lang.String
     */
    @PostMapping
    public String uploadToLocalFile(@RequestParam MultipartFile file, String secret) {
        String msg = "上传文件";

        // 校验 ip 白名单、webhook 密钥
        if (!commonService.check(secret, msg)) {
            return "失败";
        }
        // 文件非空
        if (file.isEmpty()) {
            System.out.println(msg + "失败，请选择文件");
            return "失败";
        }

        String path = FileUtil.uploadToLocalFile(filePath, file);
        if (StringUtils.isEmpty(path)) {
            System.out.println(msg + "失败，请联系管理员");
            return "失败";
        }

        return path;
    }
}