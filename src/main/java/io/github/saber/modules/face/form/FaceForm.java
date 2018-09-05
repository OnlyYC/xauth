package io.github.saber.modules.face.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FaceForm {
    @NotNull
    private String imageData;
    @NotNull
    private String principal;
}
