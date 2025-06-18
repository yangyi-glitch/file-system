package com.minio.console.dao;

import lombok.Data;

@Data
public class ResultDAO<T> {
    private T data;
    private Long total;
    private String msg;

    public ResultDAO(T data) {
        this.data = data;
    }

    public ResultDAO(T data, Long total) {
        this.data = data;
        this.total = total;
    }

    public ResultDAO(String msg) {
        this.msg = msg;
    }

    public static <T> ResultDAO success(T data) {
        return new ResultDAO<>(data);
    }

    public static <T> ResultDAO success(T data, Long total) {
        return new ResultDAO<>(data, total);
    }
}
