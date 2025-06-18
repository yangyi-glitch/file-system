package com.minio.console.controller;

import com.minio.console.dao.LoginReqVO;
import com.minio.console.dao.ResultDAO;
import com.minio.console.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RequestMapping("/user")
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public ResultDAO login(@RequestBody LoginReqVO reqVO) {
        return userService.login(reqVO);
    }
}
