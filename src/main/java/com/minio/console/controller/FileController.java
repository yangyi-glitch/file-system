package com.minio.console.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minio.console.dao.*;
import com.minio.console.entity.FileDTO;
import com.minio.console.service.file.FileService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static com.minio.console.dao.ResultDAO.success;

@RequestMapping("/file")
@RestController
public class FileController {

    @Resource
    private FileService fileService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/list")
    public ResultDAO list(FileReqDAO req) {
        Page<FileDTO> fileDTOPage = fileService.fileList(req);
        return success(fileDTOPage.getRecords(), fileDTOPage.getTotal());
    }

    @GetMapping("/area")
    public ResultDAO treeList() {
        String area = stringRedisTemplate.opsForValue().get("area");
        List<AreaRespDAO> areaRespVOS = JSONObject.parseArray(area, AreaRespDAO.class);
        return success(areaRespVOS);
    }

    @PostMapping("/delet")
    public void list(@RequestParam("id") Long id) {
        fileService.removeById(id);
    }

    @PostMapping("/upload")
    public boolean upload(@RequestParam("file") MultipartFile file) {
        return fileService.upload(file);
    }

    @PostMapping("/convert")
    public ResultDAO convert(@RequestParam("file") MultipartFile file) {
        FileDTO fileDTO = fileService.convert(file);
        return success(fileDTO);
    }

    @GetMapping("/preview")
    public void preview(HttpServletResponse response, IdDAO req) {
        fileService.preview(response, req.getId());
    }

    @GetMapping("/previewUrl")
    public ResultDAO previewUrl(IdDAO req) {
        return success(fileService.previewUrl(req.getId()));
    }

    @GetMapping("/download")
    public DownloadDAO download(@RequestParam("id") Long id) {
        return fileService.download(id);
    }

    @GetMapping("/downloadLocal")
    public void downloadLocal(HttpServletResponse response, @RequestParam("id") Long id) {
        fileService.downloadLocal(response, id);
    }
}
