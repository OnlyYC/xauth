package com.liaoyb.auth.web.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class FaceIdentifyException extends AbstractThrowableProblem {
    public FaceIdentifyException(String message) {
        super(ErrorConstants.DEFAULT_TYPE, message, Status.INTERNAL_SERVER_ERROR);
    }
}
