package com.minio.console.interceptor;

import com.minio.console.entity.UserDTO;
import com.minio.console.util.ThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.minio.console.util.JwtUtils.parseToken;

@Component
@Slf4j
public class RequestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取 token
        String token = request.getHeader("Token");
        if (token == null || token.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "请先登录~");
            return false;
        }
        try {
            // 使用 JwtUtils 解析 token
            UserDTO userDTO = parseToken(token);
            if (userDTO == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "token失效~");
                return false;
            }
            ThreadLocalUtils.set(userDTO);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "token失效~");
            return false;
        }
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("请求结束，释放资源 =====> {}", ThreadLocalUtils.getLoginUser().getUsername());
        ThreadLocalUtils.remove();
    }
}
