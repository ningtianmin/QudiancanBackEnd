package com.qudiancan.backend.controller.shop;

import com.qudiancan.backend.common.Constant;
import com.qudiancan.backend.common.ShopAccountHolder;
import com.qudiancan.backend.common.ShopRequiredAuthority;
import com.qudiancan.backend.enums.shop.ShopAuthorityEnum;
import com.qudiancan.backend.pojo.Response;
import com.qudiancan.backend.pojo.po.BranchPO;
import com.qudiancan.backend.pojo.po.ShopPO;
import com.qudiancan.backend.pojo.vo.shop.ShopVO;
import com.qudiancan.backend.service.shop.ShopAccountService;
import com.qudiancan.backend.service.shop.ShopService;
import com.qudiancan.backend.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author NINGTIANMIN
 */
@RestController
@RequestMapping("/shops")
@Slf4j
public class ShopController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopAccountService shopAccountService;

    /**
     * 获取店铺
     *
     * @param shopId 店铺id
     * @return 获取的店铺
     */
    @GetMapping("/{shopId}")
    @ShopRequiredAuthority(ShopAuthorityEnum.SHOP_SHOP_SHOW)
    public Response<ShopPO> getShop(@PathVariable String shopId) {
        return Response.success(shopService.getShop(ShopAccountHolder.get().getId(), shopId));
    }

    /**
     * 更新店铺
     *
     * @param shopVO 店铺信息
     * @return 更新后的店铺
     */
    @PostMapping("/{shopId}")
    @ShopRequiredAuthority(ShopAuthorityEnum.SHOP_SHOP_UPDATE)
    public Response<ShopPO> updateShop(@PathVariable String shopId, ShopVO shopVO) {
        return Response.success(shopService.updateShop(ShopAccountHolder.get().getId(), shopId, shopVO));
    }

    /**
     * 账户登出
     */
    @PostMapping("/logout")
    public Response logout(HttpServletRequest request, HttpServletResponse response) {
        shopAccountService.logout(request, response);
        return Response.success();
    }

    /**
     * 完善店铺
     *
     * @param perfectShopVO 店铺和第一个门店信息
     * @return 请求状态
     */
    @PostMapping("/perfect")
    public Response perfectShop(PerfectShopVO perfectShopVO, HttpServletResponse servletResponse) {
        BranchPO branchPO = shopService.perfectShop(ShopAccountHolder.get().getId(), perfectShopVO);
        CookieUtil.set(servletResponse, Constant.COOKIE_CURRENT_BRANCH_ID, branchPO.getId() + "", Constant.COOKIE_EXPIRY);
        return Response.success();
    }

}
