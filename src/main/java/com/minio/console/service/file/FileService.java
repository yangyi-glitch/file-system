package com.minio.console.service.file;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minio.console.dao.*;
import com.minio.console.entity.FileDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface FileService extends IService<FileDTO> {

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    boolean upload(MultipartFile file);

    /**
     * 预览文件
     *
     * @param response
     * @param id
     * @return
     */
    boolean preview(HttpServletResponse response, Long id);

    /**
     * 获取预览链接 一小时过期时间
     *
     * @param id
     * @return
     */
    String previewUrl(Long id);

    /**
     * 下载文件
     *
     * @param response
     * @param id
     */
    DownloadDAO download(Long id);

    /**
     * 下载到本地
     *
     * @param response
     * @param id
     * @return
     */
    boolean downloadLocal(HttpServletResponse response, Long id);

    /**
     * 获取附件列表
     *
     * @return
     */
    Page<FileDTO> fileList(FileReqDAO req);

    /**
     * 文件转换
     * @param file
     * @return
     */
    FileDTO convert(MultipartFile file);
}
