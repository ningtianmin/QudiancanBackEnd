package com.qudiancan.backend.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author NINGTIANMIN
 */
@Data
@AllArgsConstructor
public class AccountTokenDTO {
    private Integer id;
    private String token;
}
