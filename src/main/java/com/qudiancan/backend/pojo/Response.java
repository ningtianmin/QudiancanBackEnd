package com.qudiancan.backend.pojo;

import com.qudiancan.backend.enums.ResponseEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author NINGTIANMIN
 */
@Data
@NoArgsConstructor
public class Response<T> {

    private Integer code;
    private String message;
    private T data;

    private Response(ResponseEnum responseEnum) {
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }

    private Response(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Response(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Response success() {
        return new Response(ResponseEnum.OK);
    }

    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>(ResponseEnum.OK);
        response.setData(data);
        return response;
    }

    public static Response failure(ResponseEnum responseEnum) {
        return new Response(responseEnum);
    }

    public static <T> Response<T> failure(ResponseEnum responseEnum, T data) {
        Response<T> response = new Response<>(responseEnum);
        response.setData(data);
        return response;
    }

    public static Response failure(Integer code, String message) {
        return new Response(code, message);
    }

    public static Response failure(String message) {
        Response response = new Response();
        response.setMessage(message);
        return response;
    }

    public static <T> Response<T> failure(Integer code, String message, T data) {
        return new Response<>(code, message, data);
    }

}
