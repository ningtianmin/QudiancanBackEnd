package com.qudiancan.backend.pojo.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author NINGTIANMIN
 */
@Entity(name = "CartProduct")
@Data
public class CartProductPO {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer cartId;
    private Integer productId;
    private Integer productNum;
    private String productName;
}
