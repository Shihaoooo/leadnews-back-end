package com.nobody.utils;


import io.jsonwebtoken.*;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class JWTUtils {

    private final String secretKey = "bGVhZG5ld3M="; // leadnews

    private final Date expire = new Date(3600000);

    public String generateToken(String id) {
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .addClaims(Map.of("id", id))
                .setExpiration(expire)
                .compact();
    }

    public String generateToken(Long id) {
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .addClaims(Map.of("id", id))
                .setExpiration(expire)
                .compact();
    }

    /**
     * 获取payload body信息
     *
     * @param token
     * @return
     */
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

    /**
     * 获取token中的claims信息
     *
     * @param token
     * @return
     */
    private Jws<Claims> getJws(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
    }


    /**
     * 是否过期
     *
     * @param claims
     * @return -1：有效，0：有效，1：过期，2：过期
     */
    public int verifyToken(Claims claims) throws Exception {
        if (claims == null) {
            return 1;
        }

        claims.getExpiration().before(new Date());
        // 需要自动刷新TOKEN
        if ((claims.getExpiration().getTime() - System.currentTimeMillis()) > expire.getTime()) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * 获取hearder body信息
     *
     * @param token
     * @return
     */
    public JwsHeader getHeaderBody(String token) {
        return getJws(token).getHeader();
    }

}