package io.github.saber.modules.face.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.saber.common.utils.JWTUtil;
import io.github.saber.web.errors.InternalServerErrorException;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("jWTFaceCredentialsProvider")
@Slf4j
public class JWTFaceCredentialsProvider implements FaceCredentialsProvider {
    @Autowired
    @Qualifier("faceCredentialJwt")
    private JWTUtil jwtUtil;


    @Override
    public String generate(FaceIdentifyCredentials faceIdentifyCredentials) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(faceIdentifyCredentials);
            return jwtUtil.createToken(faceIdentifyCredentials.getUsername(), json);
        } catch (JsonProcessingException e) {
            log.error("转换识别信息异常", e);
            throw new InternalServerErrorException("转换识别信息异常");
        }
    }

    @Override
    public FaceIdentifyCredentials getFaceIdentifyCredentials(String faceCredentials) {
        Claims claims = jwtUtil.getClaimByToken(faceCredentials);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(claims.get(jwtUtil.getExtraKey()).toString(), FaceIdentifyCredentials.class);
        } catch (IOException e) {
            throw new InternalServerErrorException("从token中转换识别信息异常");
        }
    }

    @Override
    public boolean valid(String faceCredentials) {
        return jwtUtil.validateToken(faceCredentials);
    }
}
