package com.minio.console.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minio.console.dao.PageDAO;
import com.minio.console.entity.ArticleDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<ArticleDTO> {


    @Select("select * from article ORDER BY id DESC limit #{pageNo}, #{pageSize}")
    List<ArticleDTO> listPage(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);
}
