package com.minio.console.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user")
@Data
public class UserDTO {
    @TableId(type = IdType.AUTO)
    private Long userId;
    private String account;
    private String username;
    private String password;
    private String email;
    private String created;
    private String lastModified;
    private String role;
}
