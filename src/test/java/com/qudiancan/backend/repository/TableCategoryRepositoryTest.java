package com.qudiancan.backend.repository;

import com.qudiancan.backend.BackEndApplicationTests;
import com.qudiancan.backend.pojo.po.TableCategoryPO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public class TableCategoryRepositoryTest extends BackEndApplicationTests {
    @Autowired
    private TableCategoryRepository repository;

    @Test
    public void testFindAll() {
        List<TableCategoryPO> all = repository.findAll();
        Assert.assertNotEquals(0, all.size());
    }
}