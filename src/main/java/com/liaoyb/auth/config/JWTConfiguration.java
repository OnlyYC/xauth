package com.liaoyb.auth.config;

import com.liaoyb.auth.common.utils.JWTUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTConfiguration {

    @Bean
    public JWTUtil faceCredentialJwt() {
        //有效时间 5分钟
        JWTUtil jwtUtil = new JWTUtil(1000 * 60 * 5);
        return jwtUtil;
    }
}
