package com.minio.console.service.file;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minio.console.dao.*;
import com.minio.console.entity.FileDTO;
import com.minio.console.mapper.FileMapper;
import com.minio.console.service.minio.MinioService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.minio.console.enums.ContentTypeEnums.getContentType;
import static com.minio.console.util.FileUtils.*;
import static com.minio.console.util.converter.PDFToWordConverter.escalateConvert_pdf;
import static com.minio.console.util.converter.WordToPDFConverter.escalateConvert_word;

@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FileDTO> implements FileService {
    @Resource
    private MinioService minioService;
    @Resource
    private FileMapper fileMapper;

    @Override
    public boolean upload(MultipartFile file) {
        if (ObjectUtil.isNull(file)) {
            return false;
        }
        //获取后缀
        String suffix = getFileSuffix(file.getOriginalFilename());
        //拼接路径
        String url = getFileUrl(suffix, file.getOriginalFilename());
        //上传minio
        boolean upload = minioService.upload(url, file, suffix);
        //保存文件数据库
        if (upload) {
//            FileDTO convert = this.convert(file);
            boolean insert = fileMapper.insertFile(file.getOriginalFilename().split("\\.")[0], url, suffix, null);
            if (!insert) {
                minioService.remove(url);
                return insert;
            }
        }
        return true;
    }

    @Override
    public boolean preview(HttpServletResponse response, Long id) {
        FileDTO fileDTO = fileMapper.selectById(id);
        if (Objects.isNull(fileDTO)) {
            return false;
        }
        if ("docx".equals(fileDTO.getFileType()) || "doc".equals(fileDTO.getFileType())) {
            return minioService.perviewDoc(fileDTO.getFileUrl(), fileDTO.getFileName());
        }
        return minioService.perview(response, fileDTO.getFileUrl());
    }

    @Override
    public String previewUrl(Long id) {
        FileDTO fileDTO = fileMapper.selectById(id);
        if (Objects.isNull(fileDTO)) {
            return null;
        }
        if ("docx".equals(fileDTO.getFileType()) || "doc".equals(fileDTO.getFileType())) {
            return fileDTO.getWordUrl();
        }
        return minioService.perviewUrl(fileDTO.getFileUrl());
    }

    @Override
    public DownloadDAO download(Long id) {
        FileDTO fileDTO = fileMapper.selectById(id);
        if (Objects.isNull(fileDTO)) {
            return null;
        }
        String path = minioService.perviewUrl(fileDTO.getFileUrl());
        DownloadDAO downloadDAO = new DownloadDAO();
        downloadDAO.setUrl(path);
        downloadDAO.setFileName(fileDTO.getFileName());
        return downloadDAO;
    }

    @Override
    public boolean downloadLocal(HttpServletResponse response, Long id) {
        FileDTO fileDTO = fileMapper.selectById(id);
        if (Objects.isNull(fileDTO)) {
            return false;
        }
        return minioService.downloadLocal(fileDTO.getFileUrl(), fileDTO.getFileName());
    }

    @Override
    public Page<FileDTO> fileList(FileReqDAO req) {
        Page<FileDTO> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<FileDTO> fileDTOPage = fileMapper.selectPage(page, new LambdaQueryWrapper<FileDTO>()
                .like(StringUtils.isNotEmpty(req.getFileName()), FileDTO::getFileName, req.getFileName())
                .like(StringUtils.isNotEmpty(req.getFileType()), FileDTO::getFileType, req.getFileType())
                .ne(FileDTO::getFileType, "jpg_pay")
                .orderByDesc(FileDTO::getId));
//        for (FileDTO fileDTO : fileDTOPage.getRecords()) {
//            fileDTO.setFileUrl(minioService.perviewUrl(fileDTO.getFileUrl()));
//        }
        return fileDTOPage;
    }

    @Override
    public FileDTO convert(MultipartFile file) {
        FileDTO fileDTO = new FileDTO();
        try {
            //获取后缀
            String suffix = getFileSuffix(file.getOriginalFilename());
            switch (suffix) {
                case "pdf":
                    byte[] bytes_word = escalateConvert_pdf(file.getInputStream());
                    String url_word = conventerUrl(file.getOriginalFilename(), "docx");
                    minioService.upload(bytes_word, getContentType("docx"), url_word);
                    fileDTO.setFileName(conventerName(file.getOriginalFilename(), "docx"));
                    fileDTO.setFileUrl(url_word);
                    break;
                case "docx":
                    byte[] bytes_pdf = escalateConvert_word(file.getInputStream());
                    String url_pdf = conventerUrl(file.getOriginalFilename(), "pdf");
                    minioService.upload(bytes_pdf, getContentType("pdf"), url_pdf);
                    fileDTO.setFileName(conventerName(file.getOriginalFilename(), "pdf"));
                    fileDTO.setFileUrl(url_pdf);
                    break;
                default:
                    return null;
            }
            String http_url = minioService.perviewUrl(fileDTO.getFileUrl());
            fileDTO.setFileUrl(http_url);
            return fileDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
