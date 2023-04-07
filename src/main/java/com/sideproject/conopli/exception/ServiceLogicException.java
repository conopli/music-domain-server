package com.sideproject.conopli.exception;

import com.sideproject.conopli.constant.ErrorCode;
import lombok.Getter;

@Getter
public class ServiceLogicException extends RuntimeException {

    private ErrorCode errorCode;

    public ServiceLogicException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
