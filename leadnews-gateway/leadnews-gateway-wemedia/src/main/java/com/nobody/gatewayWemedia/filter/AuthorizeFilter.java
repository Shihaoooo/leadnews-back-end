package com.nobody.gatewayWemedia.filter;


import com.alibaba.cloud.commons.lang.StringUtils;
import com.nobody.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthorizeFilter implements Ordered, GlobalFilter {

    private final JWTUtils jwtUtils;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获取request和response对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        //2.判断是否是登录
        if(request.getURI().getPath().contains("/login") || request.getURI().getPath().contains("/test")){
            //放行
            return chain.filter(exchange);
        }

        //3.获取token
        String token = request.getHeaders().getFirst("token");

        //4.判断token是否存在
        if(StringUtils.isBlank(token)){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        ServerHttpRequest newRequest;

        //5.判断token是否有效
        try {
            Claims claimsBody = jwtUtils.tokenParser(token);
            //是否是过期
            int result = jwtUtils.verifyToken(claimsBody);
            if(result == -1){
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            // 获取用户信息
            Object userId = claimsBody.get("id");

            // 存入header中
            newRequest = request.mutate().header("userID", userId.toString()).build();
        } catch (Exception e) {
            e.printStackTrace();
            // token解析失败
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            log.error("token解析失败");
            return response.setComplete();
        }

        //6.放行
        return chain.filter(exchange.mutate().request(newRequest).build());
    }

    /**
     * 优先级设置  值越小  优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
