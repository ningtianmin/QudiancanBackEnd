package com.qudiancan.backend.repository;

import com.qudiancan.backend.pojo.po.DepartmentPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author NINGTIANMIN
 */
public interface DepartmentRepository extends JpaRepository<DepartmentPO, Integer> {
}
