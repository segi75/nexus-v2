package com.nexus.backend.common.exception;

import com.nexus.backend.common.api.ResultCode;

import lombok.Getter;

@Getter
public class BizException extends RuntimeException {

    private final ResultCode resultCode;

    public BizException(String message) {
        super(message);
        this.resultCode = ResultCode.FAIL;
    }

    public BizException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }
}