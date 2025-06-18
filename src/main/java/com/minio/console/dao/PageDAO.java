package com.minio.console.dao;

import lombok.Data;

@Data
public class PageDAO {
    private int pageNo = 1;
    private int pageSize = 10;
}
