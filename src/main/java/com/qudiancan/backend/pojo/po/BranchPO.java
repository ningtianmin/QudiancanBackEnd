package com.qudiancan.backend.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

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
    @CreatedDate
    private Timestamp createTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        BranchPO branchPO = (BranchPO) o;
        return Objects.equals(id, branchPO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
