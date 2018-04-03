package com.qudiancan.backend.controller.wechat;

import com.qudiancan.backend.pojo.Response;
import com.qudiancan.backend.pojo.dto.shop.ShopProductDTO;
import com.qudiancan.backend.service.shop.ShopProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
@RestController
@RequestMapping("/wechat")
@Slf4j
public class WechatProductController {
    @Autowired
    private ShopProductService shopProductService;

    /**
     * 获取门店上架产品信息
     *
     * @param branchId 门店id
     */
    @GetMapping("/branches/{branchId}/products")
    public Response<List<ShopProductDTO>> listProductUp(@PathVariable Integer branchId) {
        log.info("[获取门店产品信息]branchId:{}", branchId);
        return Response.success(shopProductService.listProductUp(branchId));
    }
}
