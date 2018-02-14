package com.qudiancan.backend.repository;

import com.qudiancan.backend.pojo.po.AccountPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author NINGTIANMIN
 */
public interface AccountRepository extends JpaRepository<AccountPO, Integer> {
    /**
     * 通过手机号查找账户
     *
     * @param phone 手机号
     * @return 账户
     */
    AccountPO findByPhone(String phone);

}
