package com.minio.console.dao;

import lombok.Data;

@Data
public class ArticleReqDAO extends PageDAO{
    private String bookName;
    private String author;
    private String price;
}
