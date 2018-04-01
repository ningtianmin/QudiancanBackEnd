package com.qudiancan.backend.common;

import com.qudiancan.backend.enums.shop.ShopAuthorityEnum;

import java.lang.annotation.*;

/**
 * @author NINGTIANMIN
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ShopRequiredAuthority {
    ShopAuthorityEnum value();
}
