package com.qudiancan.backend.repository;

import com.qudiancan.backend.BackEndApplicationTests;
import com.qudiancan.backend.pojo.po.CartProductPO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author NINGTIANMIN
 */
@Transactional
public class CartProductRepositoryTest extends BackEndApplicationTests {
    @Autowired
    private CartProductRepository repository;

    @Test
    public void testFindAll() {
        List<CartProductPO> all = repository.findAll();
    }
}