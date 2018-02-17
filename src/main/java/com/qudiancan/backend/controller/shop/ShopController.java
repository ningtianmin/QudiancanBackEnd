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

    @GetMapping("/{shopId}")
    @RequiredAuthority(AuthorityEnum.SHOP_SHOP_SHOW)
    public Response<ShopPO> getShop(Session session, @PathVariable String shopId) {
        log.info("[查看店铺]session:{}", session);
        return Response.success(shopService.getShop(AccountHolder.get().getId(), shopId));
    }

    @PostMapping("/{shopId}/edit")
    @RequiredAuthority(AuthorityEnum.SHOP_SHOP_UPDATE)
    public Response<ShopPO> updateShop(Session session, ShopVO shopVO) {
        log.info("[更新店铺]session:{}", session);
        return Response.success(shopService.updateShop(AccountHolder.get().getId(), shopVO));
    }
}
