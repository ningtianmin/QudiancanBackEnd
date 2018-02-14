package com.qudiancan.backend.exception;

import com.qudiancan.backend.enums.ResponseEnum;
import lombok.Getter;

/**
 * @author NINGTIANMIN
 */
@Getter
public class CashierException extends RuntimeException {
    private Integer code;

    public CashierException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
    }
}
