package com.minio.console.service.article;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minio.console.dao.ArticleReqDAO;
import com.minio.console.dao.PageDAO;
import com.minio.console.entity.ArticleDTO;
import com.minio.console.entity.FileDTO;
import com.minio.console.mapper.ArticleMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, ArticleDTO> implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Page<ArticleDTO> listPage(ArticleReqDAO req) {
        Page<ArticleDTO> page = new Page<>(req.getPageNo(), req.getPageSize());
        return articleMapper.selectPage(page, new LambdaQueryWrapper<ArticleDTO>()
                .like(StringUtils.isNotEmpty(req.getBookName()), ArticleDTO::getBookName, req.getBookName())
                .like(StringUtils.isNotEmpty(req.getAuthor()), ArticleDTO::getAuthor, req.getAuthor())
                .like(StringUtils.isNotEmpty(req.getPrice()), ArticleDTO::getPrice, req.getPrice())
                .orderByDesc(ArticleDTO::getId));
    }
}
