package com.bookStore.util;

import com.alibaba.druid.util.StringUtils;
import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
@Order(0)
@ConfigurationProperties(prefix = "jwt.token")
public class JwtHelper {

    private long tokenExpiration; //有效时间,单位毫秒 1000毫秒 == 1秒
    private String tokenSignKey;  //当前程序签名秘钥

    //生成token字符串
    public String createToken(Long userId) {
        String token = Jwts.builder()

                .setSubject("YYGH-USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration * 1000 * 60)) //单位分钟
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    //从token字符串获取userid
    public Long getUserId(String token) {
        if (StringUtils.isEmpty(token)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Long userId = (Long) claims.get("userId");
        return userId;
    }


    //判断token是否有效
    public boolean isExpiration(String token) {
        try {
            boolean isExpire = Jwts.parser()
                    .setSigningKey(tokenSignKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration().before(new Date());
            //没有过期，有效，返回false
            return isExpire;
        } catch (Exception e) {
            //过期出现异常，返回true
            return true;
        }
    }
}
