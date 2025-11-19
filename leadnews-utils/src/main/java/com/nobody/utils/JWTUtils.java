package com.nobody.utils;


import io.jsonwebtoken.*;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class JWTUtils {

    private final String secretKey = "bGVhZG5ld3M="; // leadnews

    public String generateToken(String id) {
        Date expire = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000); // 24小时后过期


        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .addClaims(Map.of("id", id))
                .setExpiration(expire)
                .compact();
    }

    public String generateToken(Long id) {
        Date expire = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000); // 24小时后过期

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
        }catch (ExpiredJwtException e) {
            throw new Exception("令牌已过期", e); // 明确是过期错误
        } catch (SignatureException e) {
            throw new Exception("令牌签名错误", e); // 签名不匹配
        } catch (MalformedJwtException e) {
            throw new Exception("令牌格式错误", e); // token格式不对
        } catch (Exception e) {
            throw new Exception("令牌解析失败", e); // 其他错误
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
     * @return -1 无有效 1 有效
     */
    public int verifyToken(Claims claims) {
        if (claims == null) {
            return -1; // 无效（无claims）
        }
        Date expireDate = claims.getExpiration();
        Date now = new Date();
        // 过期时间在当前时间之后：有效
        if (expireDate.after(now)) {
            return 1;
        } else {
            // 过期时间在当前时间之前：已过期
            return -1;
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