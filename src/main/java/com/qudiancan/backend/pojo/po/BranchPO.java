package com.qudiancan.backend.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author NINGTIANMIN
 */
@Entity(name = "Branch")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchPO {
    @Id
    @GeneratedValue
    private Integer id;
    private String shopId;
    private String name;
    private String notice;
    private String phone;
    private String address;
    private String longitude;
    private String latitude;
    private String introduction;
    private String status;
    // TODO: 18/02/17 问题:新建门店记录时返回的门店对象createTime属性为null
    
    private Timestamp createTime;
}
