package com.bookStore.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bookStore.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author wmh
 * @date 2024/01/13 15:36
 */
@Service
public class TokenService {
    public String getToken(User user){
        String token="";
        token= JWT.create().withAudience(user.getId().toString())      //将user  id 保存到 token里面
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
