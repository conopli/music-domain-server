package com.sideproject.conopli.constant;

import lombok.Getter;

public enum ErrorCode {
    BAD_REQUEST(400, "BAD REQUEST"),
    ARGUMENT_MISMATCH_BAD_REQUEST(400, "Argument Mismatch Bad Request" ),
    ACCESS_DENIED(403, "ACCESS DENIED"),
    NOT_FOUND(404, "NOT FOUND"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error" ),
    HTTP_REQUEST_IO_ERROR(500, "Generator Server Request I/O Exception" ),
    DATA_ACCESS_ERROR(500, "Data Access Error"),
    NOT_IMPLEMENTED(501,"NOT IMPLEMENTED");

    @Getter
    private final int status;

    @Getter
    private final String message;

    ErrorCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
