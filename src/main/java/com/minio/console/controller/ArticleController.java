package com.minio.console.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minio.console.dao.ArticleReqDAO;
import com.minio.console.dao.LoginReqDAO;
import com.minio.console.dao.ResultDAO;
import com.minio.console.entity.ArticleDTO;
import com.minio.console.service.article.ArticleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.minio.console.dao.ResultDAO.success;

@RequestMapping("/article")
@RestController
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @PostMapping("/add")
    public void add(@RequestBody ArticleDTO articleDTO) {
        articleService.save(articleDTO);
    }

    @GetMapping("/list")
    public ResultDAO list(ArticleReqDAO req) {
        Page<ArticleDTO> articleDTOPage = articleService.listPage(req);
        return success(articleDTOPage.getRecords(), articleDTOPage.getTotal());
    }

    @GetMapping("/get")
    public ResultDAO get(@RequestParam("id") Long id) {
        return success(articleService.getById(id));
    }

    @PostMapping("/delet")
    public void delet(@RequestParam("id") Long id) {
        articleService.removeById(id);
    }

    @PostMapping("/update")
    public void update(@RequestBody ArticleDTO articleDTO) {
        articleService.updateById(articleDTO);
    }

    @PostMapping("/login")
    public ResultDAO login(@RequestBody LoginReqDAO loginReqVO) {
        if (StringUtils.isEmpty(loginReqVO.getUsername())){
            return success("账号不能为空");
        }
        if (StringUtils.isEmpty(loginReqVO.getPassword())){
            return success("密码不能为空");
        }
        if (!"admin".equals(loginReqVO.getUsername()) && !"admin".equals(loginReqVO.getPassword())) {
            return success("账号密码不正确");
        }
        return success("登录成功");
    }
}
