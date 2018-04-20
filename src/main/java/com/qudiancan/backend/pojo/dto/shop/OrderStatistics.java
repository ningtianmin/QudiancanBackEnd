package com.qudiancan.backend.pojo.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author NINGTIANMIN
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatistics {
    private String period;
    private BigDecimal totalSum;
    private BigDecimal discountSum;
    private BigDecimal wipeSum;
    private BigDecimal chargeSum;
    private Integer orderNum;
    private Integer customerNum;
    private BigDecimal averageOrder;
    private BigDecimal averageCustomer;
}
