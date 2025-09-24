package com.nobody.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JWTUtils {

    private final String secretKey = "bGVhZG5ld3M="; // leadnews

    private final Date expire = new Date(3600000);

    public String generateToken(String id) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .addClaims(Map.of("id", id))
                .setExpiration(expire)
                .compact();
    }

    public Claims tokenParser(String token) throws Exception {

        try{
            return Jwts.parser()
                .setSigningKey(secretKey) // 指定令牌密钥
                .parseClaimsJws(token) // 解析token数据
                .getBody();
        }catch (Exception e){
            // 打印异常信息
            throw new Exception("令牌解析失败");
        }
    }
}