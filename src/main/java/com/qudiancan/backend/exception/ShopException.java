package com.qudiancan.backend.exception;

import com.qudiancan.backend.enums.ResponseEnum;
import lombok.Getter;

/**
 * @author NINGTIANMIN
 */
@Getter
public class ShopException extends RuntimeException {
    private Integer code;

    public ShopException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
    }
}
