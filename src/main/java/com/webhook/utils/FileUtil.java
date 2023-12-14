package com.webhook.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * 文件 util
 */
public class FileUtil {
    /**
     * 上传文件到本地
     *
     * @param multipartFile
     * @return java.lang.String
     */
    public static String uploadToLocalFile(String filePath, MultipartFile multipartFile) {
        filePath = filePath + multipartFile.getOriginalFilename();

        try {
            writeFileToLocal(filePath, multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath;
    }

    /**
     * 写入文件到本地
     *
     * @param filePath
     * @param inputStream
     * @return void
     */
    private static void writeFileToLocal(String filePath, InputStream inputStream) {
        try {
            /**
             * 写入本地文件
             */
            BufferedInputStream bufis = new BufferedInputStream(inputStream);

            File file = new File(filePath);
            BufferedOutputStream bufos = new BufferedOutputStream(new FileOutputStream(file));
            int len = 0;
            byte[] bt = new byte[1024];
            while ((len = bufis.read(bt)) != -1) {
                bufos.write(bt, 0, len);
            }

            bufis.close();
            bufos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
