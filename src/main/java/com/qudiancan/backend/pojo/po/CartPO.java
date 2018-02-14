package com.qudiancan.backend.pojo.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author NINGTIANMIN
 */
@Entity(name = "Cart")
@Data
public class CartPO {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer branchId;
    private String wechatId;
}
