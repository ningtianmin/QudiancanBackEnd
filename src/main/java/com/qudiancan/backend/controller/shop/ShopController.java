package com.qudiancan.backend.controller.shop;

import com.qudiancan.backend.common.AccountHolder;
import com.qudiancan.backend.common.RequiredAuthority;
import com.qudiancan.backend.enums.AuthorityEnum;
import com.qudiancan.backend.pojo.Response;
import com.qudiancan.backend.pojo.Session;
import com.qudiancan.backend.pojo.po.ShopPO;
import com.qudiancan.backend.pojo.vo.ShopVO;
import com.qudiancan.backend.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author NINGTIANMIN
 */
@RestController
@RequestMapping("/shops")
@Slf4j
public class ShopController {
    @Autowired
    private ShopService shopService;

    /**
     * 获取店铺
     *
     * @param session 账户session
     * @param shopId  店铺id
     * @return 获取的店铺
     */
    @GetMapping("/{shopId}")
    @RequiredAuthority(AuthorityEnum.SHOP_SHOP_SHOW)
    public Response<ShopPO> getShop(Session session, @PathVariable String shopId) {
        log.info("[获取店铺]session:{}", session);
        return Response.success(shopService.getShop(AccountHolder.get().getId(), shopId));
    }

    /**
     * 更新店铺
     *
     * @param session 账户session
     * @param shopVO  店铺信息
     * @return 更新后的店铺
     */
    @PostMapping("/{shopId}")
    @RequiredAuthority(AuthorityEnum.SHOP_SHOP_UPDATE)
    public Response<ShopPO> updateShop(Session session, @PathVariable String shopId, ShopVO shopVO) {
        log.info("[更新店铺]session:{}", session);
        return Response.success(shopService.updateShop(AccountHolder.get().getId(), shopId, shopVO));
    }
}
