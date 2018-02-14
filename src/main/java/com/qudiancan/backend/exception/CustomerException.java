package com.qudiancan.backend.exception;

import com.qudiancan.backend.enums.ResponseEnum;
import lombok.Getter;

/**
 * @author NINGTIANMIN
 */
@Getter
public class CustomerException extends RuntimeException {
    private Integer code;

    public CustomerException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
    }
}
