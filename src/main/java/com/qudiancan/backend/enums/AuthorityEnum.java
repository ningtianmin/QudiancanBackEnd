package com.qudiancan.backend.enums;

import lombok.Getter;

/**
 * @author NINGTIANMIN
 */
@Getter
public enum AuthorityEnum {
    ;
    private Integer id;
    private String path;

    AuthorityEnum(Integer id, String path) {
        this.id = id;
        this.path = path;
    }
}
