package com.minio.console.util;

import cn.hutool.core.text.StrBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUtils {
    public static synchronized String getFileUrl(String suffix, String fileName) {
        StrBuilder url = new StrBuilder();
        url.append(suffix);
        url.append("/");
        url.append(UUID.randomUUID().toString());
        url.append("-");
        url.append(fileName);
        return url.toString();
    }

    public static synchronized String getFileSuffix(String fileName) {
        String[] split = fileName.split("\\.");
        return split[split.length - 1];
    }

    public static synchronized String conventerUrl(String fileName, String suffix) {
        String[] split = fileName.split("\\.");
        return "convert/" + UUID.randomUUID() + split[0] + "." + suffix;
    }

    public static synchronized String conventerName(String fileName, String suffix) {
        String[] split = fileName.split("\\.");
        return split[0] + "." + suffix;
    }

    public static void createFolder(String filePath) {
        Path directoryPath = Paths.get(filePath);
        boolean exists = Files.exists(directoryPath);
        try {
            if (!exists) {
                Files.createDirectories(directoryPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
