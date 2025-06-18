package com.minio.console.service.minio;

import cn.hutool.core.util.ObjectUtil;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

import static com.minio.console.util.FileUtils.createFolder;


@Slf4j
@Service
public class MinioServiceImpl implements MinioService {

    @Resource
    private MinioClient minioClient;

    //桶名
    private static String BUCKTERTNAME = "localhost-file";
    //下载本地路径
    private static String LOCAL = "D:\\localfile\\";

    @Override
    public boolean bucketExists() {
        try {
            boolean b = minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKTERTNAME).build());
            if (!b) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(BUCKTERTNAME).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean downloadLocal(String url, String fileName) {
        try {
            createFolder(LOCAL);
            minioClient.downloadObject(DownloadObjectArgs.builder()
                    .bucket(BUCKTERTNAME)
                    .object(url)
                    .filename(LOCAL + fileName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean remove(String url) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(BUCKTERTNAME)
                    .object(url)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void download(HttpServletResponse response, String url, String fileName) {
        try {
            String contentType = this.getContentType(url);
            if ("".equals(contentType)) {
                return;
            }
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setCharacterEncoding("UTF-8");
            InputStream inputStream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(BUCKTERTNAME)
                    .object(url)
                    .build());
            ServletOutputStream outputStream = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        } catch (Exception e) {

        }
    }

    @Override
    public boolean perview(HttpServletResponse response, String url) {
        String contentType = this.getContentType(url);
        if ("".equals(contentType)) {
            return false;
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType(contentType);
        response.setHeader("Content-DisPosition", "inline");
        try (InputStream in = minioClient.getObject(GetObjectArgs.builder()
                .bucket(BUCKTERTNAME)
                .object(url)
                .build());
             ServletOutputStream outputStream = response.getOutputStream()) {

            // 将输入流的数据复制到输出流
            IOUtils.copy(in, outputStream);
            outputStream.flush();
        } catch (Exception e) {
            log.info("妈的，老是报错，看的烦人~~");
            return true;
        }
        return true;
    }

    @Override
    public boolean upload(String url, MultipartFile file, String suffix) {
        try {
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(BUCKTERTNAME)
                    .object(url)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean upload_continued(String url, MultipartFile file, String suffix) {
//        try {
//            String contentType = file.getContentType();
//            InputStream inputStream = file.getInputStream();
//
//            // 1. 初始化上传
//            String uploadId = minioClient.createMultipartUpload(
//                            CreateMultipartUploadArgs.builder()
//                                    .bucket(BUCKTERTNAME)
//                                    .object(url)
//                                    .contentType(contentType)
//                                    .build())
//                    .result()
//                    .uploadId();
//
//            List<Part> parts = new ArrayList<>();
//            byte[] buffer = new byte[5 * 1024 * 1024]; // 每个分片大小：5MB
//            int partNumber = 1;
//
//            // 2. 分片上传
//            while (true) {
//                int read = inputStream.read(buffer);
//                if (read <= 0) break;
//
//                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer, 0, read);
//
//                PartETag partETag = minioClient.uploadPart(
//                        UploadPartArgs.builder()
//                                .bucket(BUCKTERTNAME)
//                                .object(url)
//                                .uploadId(uploadId)
//                                .partNumber(partNumber++)
//                                .stream(byteArrayInputStream)
//                                .size(byteArrayInputStream.available())
//                                .build());
//
//                parts.add(new Part(partETag.partNumber(), partETag.etag()));
//            }
//
//            // 3. 完成分片上传
//            minioClient.completeMultipartUpload(
//                    CompleteMultipartUploadArgs.builder()
//                            .bucket(BUCKTERTNAME)
//                            .object(url)
//                            .uploadId(uploadId)
//                            .parts(parts)
//                            .build());
//
//            inputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
        return true;
    }

    @Override
    public boolean upload(byte[] butes, String contentType, String url) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(butes);
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(BUCKTERTNAME)
                    .object(url)
                    .stream(inputStream, butes.length, -1)
                    .contentType(contentType)
                    .build());
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String perviewUrl(String url) {
        String contentType = this.getContentType(url);
        String perviewUrl = "";
        try {
            perviewUrl = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(BUCKTERTNAME)
                            .object(url)
                            .method(Method.GET)
//                            .expiry(1, TimeUnit.DAYS) // 永久有效
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
            return perviewUrl;
        }
        log.info("预览地址：" + perviewUrl);
        return perviewUrl;
    }

    @Override
    public boolean perviewDoc(String fileUrl, String fileName) {
        String fileLocal = LOCAL + fileName;
        String command = "cmd /c start " + fileLocal;
        try {
            if (new File(fileLocal).exists()) {
                Runtime.getRuntime().exec(command);
                return true;
            }
            boolean b = this.downloadLocal(fileUrl, fileName);
            if (b) {
                Runtime.getRuntime().exec(command);
            }
            new Thread(() -> {
                try {
                    Thread.currentThread().setName(fileName);
                    // 线程睡眠60秒（60000毫秒） = 60秒
                    Thread.sleep(5000);
                    // 尝试删除文件
                    File file = new File(fileLocal);
                    if (file.exists()) {
                        boolean del = file.delete();
                        while (!del) {
                            del = file.delete();
                            System.out.println(Thread.currentThread().getName());
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Failed to delete the file: " + e.getMessage());
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private String getContentType(String url) {
        String contentType = "";
        try {
            StatObjectResponse statObjectResponse = minioClient.statObject(StatObjectArgs.builder()
                    .bucket(BUCKTERTNAME)
                    .object(url)
                    .build());
            if (ObjectUtil.isNotNull(statObjectResponse)) {
                contentType = statObjectResponse.contentType();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentType;
    }
}
