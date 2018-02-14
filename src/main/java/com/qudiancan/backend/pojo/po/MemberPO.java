package com.qudiancan.backend.pojo.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author NINGTIANMIN
 */
@Entity(name = "Member")
@Data
public class MemberPO {
    @Id
    @GeneratedValue
    private Integer id;
    private String shopId;
}
