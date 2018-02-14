package com.qudiancan.backend.exception;

import com.qudiancan.backend.enums.ResponseEnum;
import lombok.Getter;

/**
 * @author NINGTIANMIN
 */
@Getter
public class OperationException extends RuntimeException {
    private Integer code;

    public OperationException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
    }
}
