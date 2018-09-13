package com.liaoyb.auth.modules.face.core;

import lombok.Data;

/**
 * 识别返回数据
 *
 * @author liaoyanbo
 */
@Data
public class FaceIdentifyCredentials {
    private String userId;
    private String username;
    private String name;
    /**
     * 识别分数
     */
    private Float score;
}
