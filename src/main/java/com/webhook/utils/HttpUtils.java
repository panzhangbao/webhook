package com.webhook.utils;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Http 工具类
 */
public class HttpUtils {

    private static final String UTF_8 = "UTF-8";
    private static final String LOCALHOST = "127.0.0.1";
    private static final String LOCAL = "本地";
    private static final String COMMA = ",";

    /**
     * getRequest
     *
     * @return javax.servlet.http.HttpServletRequest
     */
    private static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == attributes) {
            return null;
        }

        HttpServletRequest request = attributes.getRequest();
        try {
            request.setCharacterEncoding(UTF_8);
        } catch (UnsupportedEncodingException e) {
            System.out.println("\n获取 request 异常：" + e.getLocalizedMessage());
            return null;
        }

        return request;
    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
     *
     * @return java.lang.String IP
     */
    public static String getIpAddress() {
        HttpServletRequest request = getRequest();
        if (null == request) {
            return null;
        }

        String ip = request.getHeader("X-Forwarded-For");
        String unknown = "unknown";

        if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (LOCALHOST.equals(ip)) {
            InetAddress inet = null;
            try {
                inet = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                System.out.println("\nException: 获取 IP 地址异常：" + e.getLocalizedMessage());
                return null;
            }
            ip = inet.getHostAddress();
            if (StringUtils.isEmpty(ip)) {
                return LOCALHOST;
            }
        }

        if ("0:0:0:0:0:0:0:1".equals(ip) || ip.startsWith("192.168.")) {
            return LOCALHOST;
        }

        if (ip.length() > 15) {
            String[] ips = ip.split(COMMA);
            for (int index = 0; index < ips.length; index++) {
                String strIp = ips[index];
                if (!(unknown.equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }

        return ip;
    }
}
