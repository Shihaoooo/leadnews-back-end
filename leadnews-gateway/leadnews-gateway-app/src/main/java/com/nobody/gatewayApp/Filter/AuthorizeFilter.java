package com.nobody.gatewayApp.Filter;

import com.nobody.utils.JWTUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

// 鉴权过滤器
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {

    JWTUtils jwtUtils = new JWTUtils();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 0.登陆接口直接放行/测试接口直接放行
        if(request.getURI().getPath().contains("login") || request.getURI().getPath().contains("test")){
            // ---传递上下文----
            return chain.filter(exchange);
        }

        // 1. 获取token
        String token = request.getHeaders().getFirst("token");

        // 2. 解析token
        if(token == null || token.isEmpty()){
            // 设置为未鉴权
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // 直接结束过滤
            return response.setComplete();
        }

        try {
            jwtUtils.tokenParser(token);
        } catch (Exception e) {
            // token解析异常（无效的token）
            response.setStatusCode(HttpStatus.UNAUTHORIZED); // 401
            return response.setComplete();
        }

        // 放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
