package com.nobody.wemedia.interceptor;

import com.nobody.utils.ThreadLocalUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class WmTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 将得到的用户信息存入当前线程
        String userID = request.getHeader("userID");

        if (userID != null && !userID.isEmpty()){
            ThreadLocalUtils.set(userID);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 清理当前线程中的数据
        ThreadLocalUtils.remove();
    }
}
