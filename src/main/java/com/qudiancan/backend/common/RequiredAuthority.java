package com.qudiancan.backend.common;

import com.qudiancan.backend.enums.AuthorityEnum;

import java.lang.annotation.*;

/**
 * @author NINGTIANMIN
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiredAuthority {
    AuthorityEnum value();
}
