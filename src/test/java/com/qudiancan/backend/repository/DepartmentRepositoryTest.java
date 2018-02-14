package com.qudiancan.backend.repository;

import com.qudiancan.backend.BackEndApplicationTests;
import com.qudiancan.backend.pojo.po.DepartmentPO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public class DepartmentRepositoryTest extends BackEndApplicationTests {
    @Autowired
    private DepartmentRepository repository;

    @Test
    public void testFindAll() {
        List<DepartmentPO> all = repository.findAll();
        Assert.assertNotEquals(0, all.size());
    }
}