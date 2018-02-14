package com.qudiancan.backend.repository;

import com.qudiancan.backend.pojo.po.AccountPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author NINGTIANMIN
 */
public interface AccountRepository extends JpaRepository<AccountPO, Integer> {
}
