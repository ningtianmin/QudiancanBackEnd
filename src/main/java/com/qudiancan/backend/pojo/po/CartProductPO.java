package com.qudiancan.backend.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author NINGTIANMIN
 */
@Entity(name = "CartProduct")
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class CartProductPO {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer cartId;
    private Integer productId;
    private Integer productNum;
    private String productName;
    private BigDecimal productPrice;
    private BigDecimal sum;
}
