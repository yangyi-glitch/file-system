package com.minio.console.dao;

import lombok.Data;

import java.util.List;

@Data
public class AreaRespDAO {
    private String label;

    private Long value;

    private List<AreaRespDAO> children;
}
