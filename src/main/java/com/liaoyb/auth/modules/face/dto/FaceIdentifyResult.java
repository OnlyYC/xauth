package com.liaoyb.auth.modules.face.dto;

import lombok.Data;

@Data
public class FaceIdentifyResult {
    /**
     * 识别凭证
     */
    private String faceCredentials;
    /**
     * 用户名称
     */
    private String name;
}
