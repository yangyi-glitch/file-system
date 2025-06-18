package com.minio.console.util;

import com.minio.console.entity.UserDTO;

public class ThreadLocalUtils {
    //提供ThreadLocal对象
    private static final ThreadLocal<UserDTO> THREAD_LOCAL = new ThreadLocal();

    //根据键获取值
    public static UserDTO getLoginUser() {
        return THREAD_LOCAL.get();
    }

    //存储键值对
    public static void set(UserDTO value) {
        THREAD_LOCAL.set(value);
    }

    //清除ThreadLocal防止内存泄露
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
