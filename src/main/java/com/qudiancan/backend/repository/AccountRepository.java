package com.qudiancan.backend.repository;

import com.qudiancan.backend.pojo.po.AccountPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

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

    /**
     * 通过店铺id,登录id,密码查找账户
     *
     * @param shopId   店铺id
     * @param loginId  登录id
     * @param password 密码
     * @return 账户
     */
    AccountPO findByShopIdAndLoginIdAndPassword(String shopId, String loginId, String password);

    /**
     * 通过店铺id查询账户列表
     *
     * @param shopId 店铺id
     * @return 账户列表
     */
    List<AccountPO> findByShopId(String shopId);
}
