package com.qudiancan.backend.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author NINGTIANMIN
 */
@Entity(name = "BranchOrder")
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class BranchOrderPO {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer branchId;
    private String branchName;
    private Integer tableId;
    private Integer memberId;
    private String orderNumber;
    private BigDecimal totalSum;
    private BigDecimal discountSum;
    private BigDecimal wipeSum;
    private BigDecimal chargeSum;
    private String tableName;
    private String note;
    private String payMethod;
    private String payStatus;
    private String orderStatus;
    private Timestamp createTime;
    private Integer customerNum;
}
