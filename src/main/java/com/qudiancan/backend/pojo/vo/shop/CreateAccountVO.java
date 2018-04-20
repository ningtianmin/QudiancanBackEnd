package com.qudiancan.backend.pojo.vo.shop;

import lombok.Data;

/**
 * @author NINGTIANMIN
 */
@Data
public class CreateAccountVO {
    private String loginId;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String branchIds;
    private String roleIds;
    private String phoneCaptcha;
}
