package com.qudiancan.backend.exception;

import com.qudiancan.backend.enums.ResponseEnum;
import lombok.Getter;

/**
 * @author NINGTIANMIN
 */
@Getter
public class WechatException extends RuntimeException {
    private Integer code;

    public WechatException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
    }

    public WechatException(ResponseEnum responseEnum, String appendInfo) {
        super(String.format("%s,%s", responseEnum.getMessage(), appendInfo));
        this.code = responseEnum.getCode();
    }
}
