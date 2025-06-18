package com.minio.console.service.article;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minio.console.dao.ArticleReqDAO;
import com.minio.console.dao.PageDAO;
import com.minio.console.entity.ArticleDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService extends IService<ArticleDTO> {
    Page<ArticleDTO> listPage(ArticleReqDAO req);
}
