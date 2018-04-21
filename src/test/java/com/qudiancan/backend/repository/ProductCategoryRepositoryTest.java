package com.qudiancan.backend.repository;

import com.qudiancan.backend.BackEndApplicationTests;
import com.qudiancan.backend.pojo.po.ProductCategoryPO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author NINGTIANMIN
 */
@Transactional
public class ProductCategoryRepositoryTest extends BackEndApplicationTests {
    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void testFindAll() {
        List<ProductCategoryPO> all = repository.findAll();
    }
}