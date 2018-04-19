package com.qudiancan.backend.service.impl.wechat;

import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.exception.WechatException;
import com.qudiancan.backend.pojo.dto.shop.OrderDTO;
import com.qudiancan.backend.pojo.dto.wechat.TableOrderDTO;
import com.qudiancan.backend.pojo.po.BranchPO;
import com.qudiancan.backend.pojo.po.BranchTablePO;
import com.qudiancan.backend.pojo.po.MemberPO;
import com.qudiancan.backend.pojo.vo.wechat.AddProductsVO;
import com.qudiancan.backend.pojo.vo.wechat.OrderVO;
import com.qudiancan.backend.repository.BranchRepository;
import com.qudiancan.backend.repository.MemberRepository;
import com.qudiancan.backend.service.shop.ShopOrderService;
import com.qudiancan.backend.service.shop.ShopTableService;
import com.qudiancan.backend.service.wechat.WechatAccountService;
import com.qudiancan.backend.service.wechat.WechatCartService;
import com.qudiancan.backend.service.wechat.WechatOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Objects;

/**
 * @author NINGTIANMIN
 */
@Service
@Slf4j
public class WechatOrderServiceImpl implements WechatOrderService {
    @Autowired
    private ShopOrderService shopOrderService;
    @Autowired
    private WechatAccountService wechatAccountService;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private WechatCartService wechatCartService;
    @Autowired
    private ShopTableService shopTableService;
    @Autowired
    private MemberRepository memberRepository;

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public OrderDTO create(OrderVO orderVO) {
        Integer memberId = wechatAccountService.getMemberIdByBranchIdAndOpenid(orderVO.getBranchId(), orderVO.getOpenid());
        com.qudiancan.backend.pojo.vo.shop.OrderVO orderVOShop = new com.qudiancan.backend.pojo.vo.shop.OrderVO();
        BeanUtils.copyProperties(orderVO, orderVOShop);
        orderVOShop.setMemberId(memberId);
        OrderDTO savedOrderDTO = shopOrderService.create(orderVOShop);
        wechatCartService.clearCart(orderVO.getBranchId(), orderVO.getOpenid());
        return savedOrderDTO;
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public OrderDTO addProducts(AddProductsVO addProductsVO) {
        if (StringUtils.isEmpty(addProductsVO.getOpenid())) {
            throw new WechatException(ResponseEnum.PARAM_INCOMPLETE, "openid");
        }
        com.qudiancan.backend.pojo.vo.shop.AddProductsVO addProductsVOTemp = new com.qudiancan.backend.pojo.vo.shop.AddProductsVO();
        BeanUtils.copyProperties(addProductsVO, addProductsVOTemp);
        OrderDTO savedOrderDTO = shopOrderService.addProducts(addProductsVOTemp);
        wechatCartService.clearCart(savedOrderDTO.getBranchId(), addProductsVO.getOpenid());
        return savedOrderDTO;
    }

    @Override
    public TableOrderDTO getTableOrder(Integer tableId, String openid) {
        if (Objects.isNull(tableId) || StringUtils.isEmpty(openid)) {
            throw new WechatException(ResponseEnum.PARAM_INCOMPLETE, "tableId,openid");
        }
        BranchTablePO branchTablePO = shopTableService.getBranchTable(tableId);
        if (Objects.isNull(branchTablePO)) {
            throw new WechatException(ResponseEnum.PARAM_INVALID, "tableId");
        }
        BranchPO branchPO = branchRepository.findOne(branchTablePO.getBranchId());
        MemberPO memberPO = memberRepository.findByShopIdAndOpenid(branchPO.getShopId(), openid);
        if (Objects.isNull(memberPO)) {
            throw new WechatException(ResponseEnum.PARAM_INVALID, "openid");
        }
        if (Objects.isNull(branchTablePO.getOrderId())) {
            return new TableOrderDTO(branchTablePO, null);
        }
        OrderDTO orderDTO = shopOrderService.findByOrderId(branchTablePO.getOrderId());
        return new TableOrderDTO(branchTablePO, orderDTO);
    }
}
