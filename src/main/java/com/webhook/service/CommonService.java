package com.webhook.service;

/**
 * 通用 service
 */
public interface CommonService {

    /**
     * 校验 ip 白名单、webhook 密钥
     *
     * @param secret webhook 密钥
     * @param msg    消息
     * @return boolean
     */
    boolean check(String secret, String msg);
}
