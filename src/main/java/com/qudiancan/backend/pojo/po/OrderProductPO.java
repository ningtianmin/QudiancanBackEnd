package com.qudiancan.backend.pojo.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author NINGTIANMIN
 */
@Entity(name = "OrderProduct")
@Data
public class OrderProductPO {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private String name;
    private BigDecimal price;
    private Integer num;
    private BigDecimal totalSum;
    private String status;
}
