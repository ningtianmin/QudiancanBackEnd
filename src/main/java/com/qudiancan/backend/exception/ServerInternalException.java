package com.qudiancan.backend.exception;

import com.qudiancan.backend.enums.ResponseEnum;

/**
 * @author NINGTIANMIN
 */
public class ServerInternalException extends RuntimeException {
    private Integer code;

    public ServerInternalException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
    }

    public ServerInternalException(ResponseEnum responseEnum, String appendInfo) {
        super(String.format("%s,%s", responseEnum.getMessage(), appendInfo));
        this.code = responseEnum.getCode();
    }
}
