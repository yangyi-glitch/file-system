package com.minio.console.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minio.console.dao.LoginReqVO;
import com.minio.console.dao.ResultDAO;
import com.minio.console.entity.UserDTO;

public interface UserService extends IService<UserDTO> {
    ResultDAO login(LoginReqVO reqVO);
}
