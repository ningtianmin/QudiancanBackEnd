package com.qudiancan.backend.pojo.vo.shop;

import lombok.Data;

/**
 * @author NINGTIANMIN
 */
@Data
public class RegisterVO {
    private String shopId;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String phoneCaptcha;
}
