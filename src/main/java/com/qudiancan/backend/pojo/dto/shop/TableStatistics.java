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
public class TableStatistics {
    private String description;
    private Integer orderNum;
    private Integer customerNum;
    private BigDecimal chargeSum;
    private BigDecimal averageOrder;
    private BigDecimal averageCustomer;
}
