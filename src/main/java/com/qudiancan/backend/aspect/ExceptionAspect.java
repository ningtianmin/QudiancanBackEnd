package com.qudiancan.backend.aspect;

import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author NINGTIANMIN
 */
@ControllerAdvice
public class ExceptionAspect {
    @ExceptionHandler({ShopException.class})
    @ResponseBody
    public Response handlerException(Exception e) {
        ShopException shopException = (ShopException) e;
        return Response.failure(shopException.getCode(), shopException.getMessage());
    }
}
