package com.qudiancan.backend.repository;

import com.qudiancan.backend.BackEndApplicationTests;
import com.qudiancan.backend.pojo.po.CartProductPO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public class CartProductRepositoryTest extends BackEndApplicationTests {
    @Autowired
    private CartProductRepository repository;

    @Test
    public void testFindAll() {
        List<CartProductPO> all = repository.findAll();
        Assert.assertNotEquals(0, all.size());
    }
}