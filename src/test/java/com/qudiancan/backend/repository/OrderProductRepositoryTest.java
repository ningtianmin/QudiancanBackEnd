package com.qudiancan.backend.repository;

import com.qudiancan.backend.BackEndApplicationTests;
import com.qudiancan.backend.pojo.po.OrderProductPO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author NINGTIANMIN
 */
@Transactional
public class OrderProductRepositoryTest extends BackEndApplicationTests {
    @Autowired
    private OrderProductRepository repository;

    @Test
    public void testFindAll() {
        List<OrderProductPO> all = repository.findAll();
    }
}