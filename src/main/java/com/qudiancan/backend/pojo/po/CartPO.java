package com.qudiancan.backend.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

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
@DynamicInsert
public class CartPO {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer branchId;
    private Integer memberId;
}
