package io.github.saber.modules.face.controller;

import io.github.saber.modules.face.dto.FaceIdentifyResult;
import io.github.saber.modules.face.form.FaceForm;
import io.github.saber.modules.face.service.FaceIdentifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class FaceIdentifyController {
    @Autowired
    private FaceIdentifyService faceIdentifyService;

    @PostMapping("/face/identify")
    public FaceIdentifyResult faceIdentify(@Valid @RequestBody FaceForm faceForm) {
        log.debug("REST request to identify: {}", faceForm);
        return faceIdentifyService.faceIdentify(faceForm.getPrincipal(), faceForm.getImageData());
    }
}
