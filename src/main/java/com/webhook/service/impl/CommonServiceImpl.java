package com.webhook.service.impl;

import com.webhook.service.CommonService;
import com.webhook.utils.DateUtils;
import com.webhook.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 通用 serviceImpl
 */
@Service
public class CommonServiceImpl implements CommonService {
    /**
     * 是否启用 IP 白名单（0：禁用；1：启用）
     */
    @Value("${webhook.ip.whitelist.enabled}")
    private Boolean ipEnabled;

    /**
     * IP 白名单
     */
    @Value("${webhook.ip.whitelist.content}")
    private List<String> ipList;

    /**
     * 是否启用 webhook 密钥（0：禁用；1：启用）
     */
    @Value("${webhook.secret.enabled}")
    private Boolean webhookSecretEnabled;

    /**
     * webhook 密钥，防止接口被盗用
     */
    @Value("${webhook.secret.content}")
    private String webhookSecret;

    /**
     * 校验 ip 白名单、webhook 密钥
     *
     * @param secret webhook 密钥
     * @param msg    消息
     * @return boolean
     */
    @Override
    public boolean check(String secret, String msg) {
        String ip = HttpUtils.getIpAddress();
        String date = DateUtils.dateToString(new Date());
        msg = date + " IP：" + ip + " " + msg;

        // IP 白名单校验
        if (ipEnabled && !ipList.contains(ip)) {
            System.out.println(msg + "失败，IP 错误，IP 为 " + ip);
            return false;
        }
        // webhook 密钥校验
        if (webhookSecretEnabled && !webhookSecret.equals(secret)) {
            System.out.println(msg + "失败，密钥错误，密钥为 " + secret);
            return false;
        }

        return true;
    }
}
