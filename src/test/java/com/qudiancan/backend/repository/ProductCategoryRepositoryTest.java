package com.qudiancan.backend.repository;

import com.qudiancan.backend.BackEndApplicationTests;
import com.qudiancan.backend.pojo.po.ProductCategoryPO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public class ProductCategoryRepositoryTest extends BackEndApplicationTests {
    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void testFindAll() {
        List<ProductCategoryPO> all = repository.findAll();
        Assert.assertNotEquals(0, all.size());
    }
}