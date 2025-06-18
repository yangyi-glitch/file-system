package com.minio.console.dao;

import lombok.Data;

@Data
public class FileReqDAO extends PageDAO{
    private String fileName;
    private String fileType;
}
