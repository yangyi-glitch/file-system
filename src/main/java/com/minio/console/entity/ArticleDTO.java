package com.minio.console.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("article")
public class ArticleDTO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String bookName;
    private String price;
    private String author;
}
