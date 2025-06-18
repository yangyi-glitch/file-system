package com.minio.console.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minio.console.entity.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserDTO> {
}
