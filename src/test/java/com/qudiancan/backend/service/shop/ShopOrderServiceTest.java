package com.qudiancan.backend.service.shop;

import com.qudiancan.backend.BackEndApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

/**
 * @author NINGTIANMIN
 */
@Transactional
public class ShopOrderServiceTest extends BackEndApplicationTests {
    @Autowired
    private ShopOrderService shopOrderService;

    @Test
    public void testCreate() {
        // List<CartVO> cartVOList = new ArrayList<>(3);
        // cartVOList.add(new CartVO(1, 2));
        // cartVOList.add(new CartVO(1, 1));
        // cartVOList.add(new CartVO(1, 1));
        // OrderVO orderVO = new OrderVO(1, null, null, 1, 2,
        //         "[{\"productId\":1,\"productNum\":1}]", cartVOList);
        // OrderDTO orderDTO = shopOrderService.create(orderVO);
    }

    @Test
    public void testFindByOrderNum() {
        // Assert.assertNotNull(shopOrderService.findByOrderNum("201804037911717523"));
    }

    @Test
    public void testFindByOrderId() {
        // OrderDTO orderDTO = shopOrderService.findByOrderId(2);
    }

    @Test
    public void testList() {
        // Page<OrderDTO> result = shopOrderService.list(1, new PageRequest(0, 10));
    }

    @Test
    public void testCancel() {
        // OrderDTO result = shopOrderService.cancel("201804039921119492");
    }
}