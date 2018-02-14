package com.qudiancan.backend.repository;

import com.qudiancan.backend.pojo.po.MemberPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author NINGTIANMIN
 */
public interface MemberRepository extends JpaRepository<MemberPO, Integer> {
}
