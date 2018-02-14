package com.qudiancan.backend.repository;

import com.qudiancan.backend.BackEndApplicationTests;
import com.qudiancan.backend.pojo.po.ShopPO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public class ShopRepositoryTest extends BackEndApplicationTests {
    @Autowired
    private ShopRepository repository;

    @Test
    public void testFindAll() {
        List<ShopPO> all = repository.findAll();
        Assert.assertNotEquals(0, all.size());
    }
}