package com.qudiancan.backend.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author NINGTIANMIN
 */
@Entity(name = "Member")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberPO {
    @Id
    @GeneratedValue
    private Integer id;
    private String shopId;
    private String openid;
    // TODO: 18/04/16 会员微信用户名，且要加入订单等其他表中
}
