package com.minio.console.dao;

import lombok.Data;

@Data
public class LoginReqDAO {
    private String username;
    private String password;
    private String role;
}
