package com.liaoyb.auth.modules.face.service;

import com.liaoyb.auth.modules.face.dto.FaceIdentifyResult;

/**
 * 识别
 *
 * @author liaoyanbo
 */
public interface FaceIdentifyService {
    FaceIdentifyResult faceIdentify(String principal, String imageData);
}
