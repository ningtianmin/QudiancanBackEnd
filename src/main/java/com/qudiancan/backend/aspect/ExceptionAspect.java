package com.qudiancan.backend.aspect;

import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author NINGTIANMIN
 */
@ControllerAdvice
@Slf4j
public class ExceptionAspect {

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Response handlerException(Exception e) {
        if (e instanceof ShopException) {
            ShopException shopException = (ShopException) e;
            log.warn("code：{}", shopException.getCode(), e);
            return Response.failure(shopException.getCode(), shopException.getMessage());
        } else {
            log.error("", e);
            return Response.failure(e.getMessage());
        }
    }

}
