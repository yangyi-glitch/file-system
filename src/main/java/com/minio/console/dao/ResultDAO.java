package com.minio.console.dao;

import lombok.Data;

@Data
public class ResultDAO<T> {
    private T data;
    private int code;
    private Long total;
    private String msg;

    public ResultDAO(T data, int code) {
        this.data = data;
        this.code = code;
    }

    public ResultDAO(T data, Long total, int code) {
        this.data = data;
        this.total = total;
        this.code = code;
    }

    public static <T> ResultDAO success(T data) {
        return new ResultDAO<>(data, 200);
    }

    public static <T> ResultDAO success(T data, Long total) {
        return new ResultDAO<>(data, total, 200);
    }

    public static <T> ResultDAO error(T data) {
        return new ResultDAO<>(data, 500);
    }
}
