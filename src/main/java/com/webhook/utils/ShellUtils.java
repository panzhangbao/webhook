package com.webhook.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * shell 工具类
 */
public class ShellUtils {

    /**
     * 执行脚本
     *
     * @param scriptPath 脚本路径
     * @param name       脚本名（无需 .sh）
     * @param msg        前缀消息
     * @return java.lang.String 成功/失败
     */
    public static String exec(String scriptPath, String name, String msg) {
        String command = "sh " + scriptPath + name + ".sh";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            System.out.println(msg + "失败，异常错误，错误为 " + e.getLocalizedMessage());
            return "失败";
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) {
                    break;
                }
            } catch (IOException e) {
                System.out.println(msg + "失败，异常错误，错误为 " + e.getLocalizedMessage());
                return "失败";
            }
        }
        try {
            int exitCode = process.waitFor();
        } catch (InterruptedException e) {
            System.out.println(msg + "失败，异常错误，错误为 " + e.getLocalizedMessage());
            return "失败";
        }

        System.out.println(msg + "成功");
        return "成功";
    }
}
