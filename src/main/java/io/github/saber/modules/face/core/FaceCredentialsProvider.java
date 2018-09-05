package io.github.saber.modules.face.core;

public interface FaceCredentialsProvider {
    /**
     * 生成识别凭证
     *
     * @param faceIdentifyCredentials
     * @return
     */
    String generate(FaceIdentifyCredentials faceIdentifyCredentials);

    /**
     * 获取识别信息
     *
     * @param faceCredentials
     * @return
     */
    FaceIdentifyCredentials getFaceIdentifyCredentials(String faceCredentials);
    /**
     * 验证人脸凭证(验证是否有效)
     *
     * @param faceCredentials
     * @return
     */
    boolean valid(String faceCredentials);
}
