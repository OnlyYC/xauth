package io.github.saber.modules.face.service;

import io.github.saber.modules.face.dto.FaceIdentifyResult;

/**
 * 识别
 *
 * @author liaoyanbo
 */
public interface FaceIdentifyService {
    FaceIdentifyResult faceIdentify(String principal, String imageData);
}
