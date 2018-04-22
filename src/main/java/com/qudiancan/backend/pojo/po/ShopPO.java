package com.qudiancan.backend.pojo.po;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author NINGTIANMIN
 */
@Entity(name = "Shop")
@Data
@DynamicInsert
public class ShopPO {
    @Id
    private String id;
    private String name;
    private String holderType;
    private String holderName;
    private String holderIdentify;
    private String telephone;
    private String introduction;
    private String status;
    private Timestamp createTime;
}
