package com.minio.console.service.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minio.console.dao.LoginReqVO;
import com.minio.console.dao.ResultDAO;
import com.minio.console.entity.UserDTO;
import com.minio.console.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.minio.console.dao.ResultDAO.error;
import static com.minio.console.dao.ResultDAO.success;
import static com.minio.console.util.JwtUtils.getToken;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDTO> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public ResultDAO login(LoginReqVO reqVO) {
        UserDTO userDTO = userMapper.selectOne(new LambdaQueryWrapper<UserDTO>()
                .eq(UserDTO::getUsername, reqVO.getUsername())
                .eq(UserDTO::getPassword, reqVO.getPassword()));
        if (userDTO == null) {
            return error("账号密码不匹配");
        }
        return success(getToken(userDTO));
    }
}
