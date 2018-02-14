package com.qudiancan.backend.pojo.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author NINGTIANMIN
 */
@Entity(name = "Branch")
@Data
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
    private Timestamp createTime;
}
