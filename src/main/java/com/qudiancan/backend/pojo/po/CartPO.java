package com.qudiancan.backend.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author NINGTIANMIN
 */
@Entity(name = "Cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartPO {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer branchId;
    private String wechatId;
}
