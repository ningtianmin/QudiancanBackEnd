package com.qudiancan.backend.service.impl.shop;

import com.qudiancan.backend.common.ShopAccountHolder;
import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.enums.shop.*;
import com.qudiancan.backend.exception.ServerInternalException;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.dto.shop.OrderDTO;
import com.qudiancan.backend.pojo.po.*;
import com.qudiancan.backend.pojo.vo.shop.AddProductsVO;
import com.qudiancan.backend.pojo.vo.shop.CartVO;
import com.qudiancan.backend.pojo.vo.shop.OrderVO;
import com.qudiancan.backend.repository.*;
import com.qudiancan.backend.service.shop.ShopBranchService;
import com.qudiancan.backend.service.shop.ShopOrderService;
import com.qudiancan.backend.service.shop.ShopTableService;
import com.qudiancan.backend.service.util.shop.ShopOrderServiceUtil;
import com.qudiancan.backend.service.wechat.WechatCartService;
import com.qudiancan.backend.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author NINGTIANMIN
 */
@Service
@Slf4j
public class ShopOrderServiceImpl implements ShopOrderService {
    private static final int DEFAULT_CUSTOMER_NUM = 2;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private BranchProductRepository branchProductRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BranchTableRepository branchTableRepository;
    @Autowired
    private BranchOrderRepository branchOrderRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private WechatCartService wechatCartService;
    @Autowired
    private ShopTableService shopTableService;
    @Autowired
    private ShopBranchService shopBranchService;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public OrderDTO create(OrderVO orderVO) {
        log.info("【创建订单】orderVO：{}", orderVO);
        // 参数格式、完整性校验
        ShopOrderServiceUtil.checkOrderVO(orderVO);
        // 参数有效性校验
        BranchPO branchPO = branchRepository.findOne(orderVO.getBranchId());
        if (Objects.isNull(branchPO)) {
            throw new ShopException(ResponseEnum.PARAM_INVALID, "branchId");
        }
        if (!ShopBranchStatus.NORMAL.getKey().equals(branchPO.getStatus())) {
            throw new ShopException(ResponseEnum.BRANCH_STATUS_UNUSUAL, branchPO.getStatus());
        }
        List<BranchProductPO> productPOList = branchProductRepository.findByIdIn(orderVO.getCart().stream()
                .map(CartVO::getProductId).collect(Collectors.toSet()));
        if (productPOList.stream().anyMatch(o -> !branchPO.getId().equals(o.getBranchId())
                || !ShopBranchProductStatus.NORMAL.name().equals(o.getStatus()))) {
            throw new ShopException(ResponseEnum.PARAM_INVALID, "cart.productId");
        }
        if (Objects.nonNull(orderVO.getMemberId())) {
            MemberPO memberPO = memberRepository.findOne(orderVO.getMemberId());
            if (Objects.isNull(memberPO) || !memberPO.getShopId().equals(branchPO.getShopId())) {
                throw new ShopException(ResponseEnum.PARAM_INVALID, "memberId");
            }
        }
        BranchTablePO branchTablePO = null;
        if (Objects.nonNull(orderVO.getBranchTableId())) {
            branchTablePO = branchTableRepository.findOne(orderVO.getBranchTableId());
            if (Objects.isNull(branchTablePO) || !branchPO.getId().equals(branchTablePO.getBranchId())) {
                throw new ShopException(ResponseEnum.PARAM_INVALID, "branchTableId");
            }
            if (!ShopBranchTableStatus.LEISURE.getKey().equals(branchTablePO.getStatus())) {
                throw new ShopException(ResponseEnum.BRANCH_TABLE_STATUS_UNUSUAL, branchTablePO.getStatus());
            }
        }

        // 计算订单金额
        Map<Integer, BranchProductPO> productPOMap = productPOList.stream().collect(Collectors.toMap(BranchProductPO::getId, o -> o));
        Optional<BigDecimal> totalSum = orderVO.getCart().stream().map(o -> productPOMap.get(o.getProductId()).getPrice().multiply(new BigDecimal(o.getProductNum())))
                .reduce(BigDecimal::add);
        if (!totalSum.isPresent()) {
            throw new ServerInternalException(ResponseEnum.SERVER_INTERNAL_ERROR);
        }

        // 添加订单
        BranchOrderPO branchOrderPO = new BranchOrderPO(null, orderVO.getBranchId(), branchPO.getName(), orderVO.getBranchTableId(), orderVO.getMemberId(), KeyUtil.generateOrderNumber(), totalSum.get(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                branchTablePO == null ? null : branchTablePO.getName(), orderVO.getNote() == null ? "" : orderVO.getNote(), null, OrderPayStatus.UNPAID.getKey(), OrderStatus.NEW.getKey(), Timestamp.valueOf(LocalDateTime.now()), Objects.isNull(orderVO.getCustomerNum()) ? DEFAULT_CUSTOMER_NUM : orderVO.getCustomerNum());
        BranchOrderPO savedBranchOrderPO = branchOrderRepository.save(branchOrderPO);

        // 添加订单产品
        List<OrderProductPO> orderProductPOList = orderVO.getCart().stream()
                .map(o -> {
                    BranchProductPO p = productPOMap.get(o.getProductId());
                    return new OrderProductPO(null, savedBranchOrderPO.getId(),
                            o.getProductId(), p.getName(), p.getPrice(), o.getProductNum(),
                            p.getPrice().multiply(new BigDecimal(o.getProductNum())), OrderProductStatus.NORMAL.name());
                })
                .collect(Collectors.toList());
        List<OrderProductPO> savedOrderProductPO = orderProductRepository.save(orderProductPOList);

        // 修改桌位状态
        if (Objects.nonNull(branchTablePO)) {
            branchTablePO.setStatus(ShopBranchTableStatus.OCCUPIED.getKey());
            branchTablePO.setOrderId(savedBranchOrderPO.getId());
            branchTableRepository.save(branchTablePO);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(savedBranchOrderPO, orderDTO);
        orderDTO.setOrderProducts(savedOrderProductPO);
        return orderDTO;
    }

    @Override
    public OrderDTO findByOrderNum(String orderNum) {
        log.info("【查询订单】orderNum：{}", orderNum);
        if (StringUtils.isEmpty(orderNum)) {
            throw new ShopException(ResponseEnum.PARAM_INCOMPLETE, "orderNum");
        }
        BranchOrderPO branchOrderPO = branchOrderRepository.findByOrderNumber(orderNum);
        if (Objects.isNull(branchOrderPO)) {
            throw new ShopException(ResponseEnum.ORDER_NOT_EXIST);
        }
        List<OrderProductPO> orderProductPOList = orderProductRepository.findByOrderId(branchOrderPO.getId());
        if (CollectionUtils.isEmpty(orderProductPOList)) {
            throw new ShopException(ResponseEnum.ORDER_PRODUCT_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(branchOrderPO, orderDTO);
        orderDTO.setOrderProducts(orderProductPOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findByOrderId(Integer orderId) {
        log.info("【查询订单】orderId：{}", orderId);
        if (Objects.isNull(orderId)) {
            throw new ShopException(ResponseEnum.PARAM_INCOMPLETE, "orderId");
        }
        BranchOrderPO branchOrderPO = branchOrderRepository.findOne(orderId);
        if (Objects.isNull(branchOrderPO)) {
            return null;
        }
        List<OrderProductPO> orderProductPOList = orderProductRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderProductPOList)) {
            throw new ShopException(ResponseEnum.ORDER_PRODUCT_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(branchOrderPO, orderDTO);
        orderDTO.setOrderProducts(orderProductPOList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> list(Integer memberId, Pageable pageable) {
        log.info("【分页查询订单】memberId：{}，pageable：{}", memberId, pageable);
        if (Objects.isNull(memberId) || Objects.isNull(pageable)) {
            throw new ShopException(ResponseEnum.PARAM_INCOMPLETE, "memberId,pageable");
        }
        Page<BranchOrderPO> branchOrderPOPage = branchOrderRepository.findByMemberIdOrderByCreateTimeDesc(memberId, pageable);
        List<OrderProductPO> orderProductPOList = orderProductRepository.findByOrderIdIn(branchOrderPOPage.getContent().stream()
                .map(BranchOrderPO::getId)
                .collect(Collectors.toList()));
        Map<Integer, List<OrderProductPO>> orderProductPOMap = orderProductPOList.stream()
                .collect(Collectors.groupingBy(OrderProductPO::getOrderId));
        List<OrderDTO> orderDTOList = branchOrderPOPage.getContent().stream()
                .map(o -> {
                    OrderDTO orderDTO = new OrderDTO();
                    BeanUtils.copyProperties(o, orderDTO);
                    List<OrderProductPO> orderProductPOS = orderProductPOMap.get(o.getId());
                    orderProductPOS.sort(Comparator.comparingInt(OrderProductPO::getId));
                    orderDTO.setOrderProducts(orderProductPOS);
                    return orderDTO;
                })
                .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()))
                .collect(Collectors.toList());
        return new PageImpl<>(orderDTOList, pageable, branchOrderPOPage.getTotalElements());
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public OrderDTO cancel(String orderNum) {
        log.info("【取消订单】orderNum：{}", orderNum);
        if (StringUtils.isEmpty(orderNum)) {
            throw new ShopException(ResponseEnum.PARAM_INCOMPLETE, "orderNum");
        }
        // 判断订单状态
        BranchOrderPO branchOrderPO = branchOrderRepository.findByOrderNumber(orderNum);
        if (Objects.isNull(branchOrderPO)) {
            throw new ShopException(ResponseEnum.PARAM_INVALID, "orderNum");
        }
        if (!OrderStatus.NEW.name().equals(branchOrderPO.getOrderStatus())) {
            throw new ShopException(ResponseEnum.ORDER_STATUS_UNUSUAL, branchOrderPO.getOrderStatus());
        }

        // 修改订单状态
        branchOrderPO.setOrderStatus(OrderStatus.CANCELED.name());
        BranchOrderPO savedBranchOrderPO = branchOrderRepository.save(branchOrderPO);
        shopTableService.leisureTable(savedBranchOrderPO.getId());

        // TODO: 18/04/03 如果已支付，则退款
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(savedBranchOrderPO, orderDTO);
        orderDTO.setOrderProducts(orderProductRepository.findByOrderId(orderDTO.getId()));
        return orderDTO;
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void finish(Set<String> orderNumbers) {
        log.info("【完结订单】orderNumbers：{}", orderNumbers);
        if (CollectionUtils.isEmpty(orderNumbers)) {
            throw new ShopException(ResponseEnum.PARAM_INCOMPLETE, "orderNumList");
        }
        List<BranchOrderPO> branchOrderPOList = branchOrderRepository.findByOrderNumberIn(orderNumbers);
        if (branchOrderPOList.size() != orderNumbers.size()) {
            throw new ShopException(ResponseEnum.PARAM_INVALID, "orderNumbers");
        }
        AccountPO accountPO = ShopAccountHolder.get();
        Set<Integer> branchIds = Arrays.stream(accountPO.getBranchIds().split(",")).map(Integer::valueOf).collect(Collectors.toSet());
        if (branchOrderPOList.stream().anyMatch(o -> !branchIds.contains(o.getBranchId()))) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        if (branchOrderPOList.stream().anyMatch(o -> !OrderStatus.NEW.name().equals(o.getOrderStatus()))) {
            throw new ShopException(ResponseEnum.ORDER_STATUS_UNUSUAL);
        }
        branchOrderPOList.forEach(o -> o.setOrderStatus(OrderStatus.NEW.name()));
        branchOrderRepository.save(branchOrderPOList);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void payByWechat(String orderNumber) {
        log.info("【支付订单】orderNumber：{}", orderNumber);
        if (StringUtils.isEmpty(orderNumber)) {
            throw new ShopException(ResponseEnum.PARAM_INCOMPLETE, "orderNumber");
        }
        BranchOrderPO branchOrderPO = branchOrderRepository.findByOrderNumber(orderNumber);
        if (Objects.isNull(branchOrderPO)) {
            throw new ShopException(ResponseEnum.PARAM_INVALID, "orderNumber");
        }
        if (!OrderStatus.NEW.getKey().equals(branchOrderPO.getOrderStatus()) || !OrderPayStatus.UNPAID.getKey().equals(branchOrderPO.getPayStatus())) {
            throw new ShopException(ResponseEnum.ORDER_STATUS_UNUSUAL, branchOrderPO.getOrderStatus() + branchOrderPO.getPayStatus());
        }
        branchOrderPO.setPayStatus(OrderPayStatus.PAID.getKey());
        branchOrderPO.setPayMethod(OrderPayMethod.WECHAT.getKey());
        // TODO: 18/04/20 折扣金额和抹零金额依赖于会员中心
        branchOrderPO.setDiscountSum(BigDecimal.ZERO);
        branchOrderPO.setWipeSum(BigDecimal.ZERO);
        branchOrderPO.setChargeSum(branchOrderPO.getTotalSum());
        branchOrderRepository.save(branchOrderPO);
        shopTableService.leisureTable(branchOrderPO.getId());
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public OrderDTO addProducts(AddProductsVO addProductsVO) {
        log.info("【追加产品】addProductsVO：{}", addProductsVO);
        // 参数格式、完整性校验
        ShopOrderServiceUtil.checkAddProductsVO(addProductsVO);
        // 参数有效性校验
        BranchOrderPO branchOrderPO = branchOrderRepository.findOne(addProductsVO.getOrderId());
        if (Objects.isNull(branchOrderPO)) {
            throw new ShopException(ResponseEnum.PARAM_INVALID, "orderId");
        }
        if (!OrderStatus.NEW.name().equals(branchOrderPO.getOrderStatus()) ||
                !OrderPayStatus.UNPAID.name().equals(branchOrderPO.getPayStatus())) {
            throw new ShopException(ResponseEnum.ORDER_STATUS_UNUSUAL);
        }
        BranchPO branchPO = branchRepository.findOne(branchOrderPO.getBranchId());
        if (!ShopBranchStatus.NORMAL.name().equals(branchPO.getStatus())) {
            throw new ShopException(ResponseEnum.BRANCH_STATUS_UNUSUAL, branchPO.getStatus());
        }
        List<BranchProductPO> productPOList = branchProductRepository.findByIdIn(addProductsVO.getCart().stream()
                .map(CartVO::getProductId).collect(Collectors.toSet()));
        if (productPOList.stream().anyMatch(o -> !branchPO.getId().equals(o.getBranchId())
                || !ShopBranchProductStatus.NORMAL.name().equals(o.getStatus()))) {
            throw new ShopException(ResponseEnum.PARAM_INVALID, "cart.productId");
        }

        // 更新订单
        Map<Integer, BranchProductPO> productPOMap = productPOList.stream().collect(Collectors.toMap(BranchProductPO::getId, o -> o));
        Optional<BigDecimal> totalSum = addProductsVO.getCart().stream().map(o -> productPOMap.get(o.getProductId()).getPrice().multiply(new BigDecimal(o.getProductNum())))
                .reduce(BigDecimal::add);
        if (!totalSum.isPresent()) {
            throw new ServerInternalException(ResponseEnum.SERVER_INTERNAL_ERROR);
        }
        branchOrderPO.setTotalSum(branchOrderPO.getTotalSum().add(totalSum.get()));
        branchOrderPO.setNote(branchOrderPO.getNote() == null ? addProductsVO.getNote()
                : branchOrderPO.getNote() + (addProductsVO.getNote() == null ? "" : addProductsVO.getNote()));
        BranchOrderPO savedBranchOrderPO = branchOrderRepository.save(branchOrderPO);

        // 追加订单产品
        List<OrderProductPO> orderProductPOList = addProductsVO.getCart().stream()
                .map(o -> {
                    BranchProductPO p = productPOMap.get(o.getProductId());
                    return new OrderProductPO(null, savedBranchOrderPO.getId(),
                            o.getProductId(), p.getName(), p.getPrice(), o.getProductNum(),
                            p.getPrice().multiply(new BigDecimal(o.getProductNum())), OrderProductStatus.NORMAL.name());
                })
                .collect(Collectors.toList());
        orderProductRepository.save(orderProductPOList);
        return findByOrderId(savedBranchOrderPO.getId());
    }

    @Override
    public List<OrderDTO> listOrderByOpenid(String openid) {
        log.info("[获取订单列表]openid:{}", openid);
        if (StringUtils.isEmpty(openid)) {
            throw new ShopException(ResponseEnum.PARAM_INCOMPLETE);
        }
        List<MemberPO> memberPOList = memberRepository.findByOpenid(openid);
        if (memberPOList.isEmpty()) {
            return Collections.emptyList();
        }
        List<BranchOrderPO> branchOrderPOList = branchOrderRepository.findByMemberIdIn(memberPOList.stream().map(MemberPO::getId).collect(Collectors.toSet()));
        List<OrderProductPO> orderProductPOList = orderProductRepository.findByOrderIdIn(branchOrderPOList.stream().map(BranchOrderPO::getId).collect(Collectors.toList()));
        Map<Integer, List<OrderProductPO>> orderProductPOMap = orderProductPOList.stream().collect(Collectors.groupingBy(OrderProductPO::getOrderId));
        return branchOrderPOList.stream()
                .map(o -> {
                    OrderDTO orderDTO = new OrderDTO();
                    BeanUtils.copyProperties(o, orderDTO);
                    orderDTO.setOrderProducts(orderProductPOMap.get(o.getId()).stream().sorted(Comparator.comparingInt(OrderProductPO::getId)).collect(Collectors.toList()));
                    return orderDTO;
                })
                .sorted(((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime())))
                .collect(Collectors.toList());
    }

    @Override
    public void payByCash(Integer accountId, String shopId, Integer branchId, String orderNumber) {
        log.info("【现金支付订单】orderNumber：{}", orderNumber);
        if (StringUtils.isEmpty(orderNumber)) {
            throw new ShopException(ResponseEnum.PARAM_INCOMPLETE, "orderNumber");
        }
        BranchOrderPO branchOrderPO = branchOrderRepository.findByOrderNumber(orderNumber);
        if (Objects.isNull(branchOrderPO)) {
            throw new ShopException(ResponseEnum.PARAM_INVALID, "orderNumber");
        }
        if (!OrderStatus.NEW.getKey().equals(branchOrderPO.getOrderStatus()) || !OrderPayStatus.UNPAID.getKey().equals(branchOrderPO.getPayStatus())) {
            throw new ShopException(ResponseEnum.ORDER_STATUS_UNUSUAL, branchOrderPO.getOrderStatus() + branchOrderPO.getPayStatus());
        }
        if (!shopBranchService.canManageBranch(accountId, shopId, branchId)) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        branchOrderPO.setPayStatus(OrderPayStatus.PAID.getKey());
        branchOrderPO.setPayMethod(OrderPayMethod.CASH.getKey());
        // TODO: 18/04/20 折扣金额和抹零金额依赖于会员中心
        branchOrderPO.setDiscountSum(BigDecimal.ZERO);
        BigDecimal chargeSum = branchOrderPO.getTotalSum().setScale(0, RoundingMode.DOWN);
        branchOrderPO.setWipeSum(branchOrderPO.getTotalSum().subtract(chargeSum));
        branchOrderPO.setChargeSum(chargeSum);
        branchOrderRepository.save(branchOrderPO);
        shopTableService.leisureTable(branchOrderPO.getId());
    }

    @Override
    public Page<OrderDTO> pageOrderByBranch(Integer accountId, Integer branchId, Pageable pageable) {
        log.info("【分页查询订单】accountId：{}，branchId：{}，pageable：{}", accountId, branchId, pageable);
        if (Objects.isNull(accountId) || Objects.isNull(branchId) || Objects.isNull(pageable)) {
            throw new ShopException(ResponseEnum.PARAM_INCOMPLETE);
        }
        AccountPO accountPO = accountRepository.findOne(accountId);
        if (Objects.isNull(accountPO)) {
            throw new ShopException(ResponseEnum.PARAM_INVALID, "accountId");
        }
        if (!shopBranchService.canManageBranch(accountId, accountPO.getShopId(), branchId)) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        Page<BranchOrderPO> branchOrderPOPage = branchOrderRepository.findByBranchIdOrderByCreateTimeDesc(branchId, pageable);
        List<OrderProductPO> orderProductPOList = orderProductRepository.findByOrderIdIn(branchOrderPOPage.getContent().stream()
                .map(BranchOrderPO::getId)
                .collect(Collectors.toList()));
        Map<Integer, List<OrderProductPO>> orderProductPOMap = orderProductPOList.stream()
                .collect(Collectors.groupingBy(OrderProductPO::getOrderId));
        List<OrderDTO> orderDTOList = branchOrderPOPage.getContent().stream()
                .map(o -> {
                    OrderDTO orderDTO = new OrderDTO();
                    BeanUtils.copyProperties(o, orderDTO);
                    List<OrderProductPO> orderProductPOS = orderProductPOMap.get(o.getId());
                    orderProductPOS.sort(Comparator.comparingInt(OrderProductPO::getId));
                    orderDTO.setOrderProducts(orderProductPOS);
                    return orderDTO;
                })
                .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()))
                .collect(Collectors.toList());
        return new PageImpl<>(orderDTOList, pageable, branchOrderPOPage.getTotalElements());
    }

}
