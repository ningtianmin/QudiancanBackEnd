package com.qudiancan.backend.repository;

import com.qudiancan.backend.pojo.po.MemberPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public interface MemberRepository extends JpaRepository<MemberPO, Integer> {
    /**
     * 通过餐厅id和openid获取会员
     *
     * @param shopId 餐厅id
     * @param openid openid
     * @return 会员
     */
    MemberPO findByShopIdAndOpenid(String shopId, String openid);

    /**
     * 通过openid获取会员列表
     *
     * @param openid 微信用户openid
     * @return 会员列表
     */
    List<MemberPO> findByOpenid(String openid);
}
