package com.liaoyb.auth.modules.face.service.impl;

import com.google.common.collect.Lists;
import com.liaoyb.auth.modules.face.core.FaceCredentialsProvider;
import com.liaoyb.auth.modules.face.core.FaceIdentifyCredentials;
import com.liaoyb.auth.modules.face.dto.FaceIdentifyResult;
import com.liaoyb.auth.modules.face.service.FaceIdentifyService;
import com.liaoyb.auth.web.errors.FaceIdentifyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaceIdentifyServiceImpl implements FaceIdentifyService {
    @Autowired
    private FaceCredentialsProvider faceCredentialsProvider;

    @Override
    public FaceIdentifyResult faceIdentify(String principal, String imageData) {
        //todo 调用引擎识别,生成一个识别凭证(可以有多种实现)
        if (!Lists.newArrayList("user1", "admin").contains(principal)) {
            throw new FaceIdentifyException("人脸识别不通过,用户名不存在");
        }

        FaceIdentifyCredentials faceIdentifyCredentials = new FaceIdentifyCredentials();
        faceIdentifyCredentials.setUsername(principal);
        faceIdentifyCredentials.setName(principal);
        faceIdentifyCredentials.setScore(0.8F);
        faceIdentifyCredentials.setUserId(principal);

        if (faceIdentifyCredentials.getScore() < 0.7F) {
            throw new FaceIdentifyException("人脸识别不通过,分数未达标");
        }
        String faceCredentials = faceCredentialsProvider.generate(faceIdentifyCredentials);
        FaceIdentifyResult faceIdentifyResult = new FaceIdentifyResult();
        faceIdentifyResult.setName(faceIdentifyCredentials.getName());

        faceIdentifyResult.setFaceCredentials(faceCredentials);

        return faceIdentifyResult;
    }
}
