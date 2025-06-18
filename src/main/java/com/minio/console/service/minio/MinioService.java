package com.minio.console.service.minio;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface MinioService {

    /**
     * 检查桶存不存在
     */
    boolean bucketExists();

    /**
     * 下载到本地
     */
    boolean downloadLocal(String url, String fileName);

    /**
     * 删除文件
     */
    boolean remove(String url);

    /**
     * 预览文件
     *
     * @param response
     * @param url
     * @return
     */
    boolean perview(HttpServletResponse response, String url);

    /**
     * 下载文件
     *
     * @param response
     * @param url
     * @param fileName
     */
    void download(HttpServletResponse response, String url, String fileName);

    /**
     * 上传文件
     *
     * @param url    路径
     * @param file   文件
     * @param suffix 后缀
     * @return
     */
    boolean upload(String url, MultipartFile file, String suffix);

    /**
     * 断点续传上传
     * @param url
     * @param file
     * @param suffix
     * @return
     */
    boolean upload_continued(String url, MultipartFile file, String suffix);

    /**
     * 针对文件转换上传
     * @param butes
     * @return
     */
    boolean upload(byte[] butes, String contentType, String url);

    /**
     * 获取预览连接
     *
     * @param url
     * @return
     */
    String perviewUrl(String url);

    /**
     * 预览doc/docx类型文件
     *
     * @param fileUrl
     * @param fileName
     * @return
     */
    boolean perviewDoc(String fileUrl, String fileName);
}
