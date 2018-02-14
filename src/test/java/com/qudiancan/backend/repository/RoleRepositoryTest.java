package com.qudiancan.backend.repository;

import com.qudiancan.backend.BackEndApplicationTests;
import com.qudiancan.backend.pojo.po.RolePO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public class RoleRepositoryTest extends BackEndApplicationTests {
    @Autowired
    private RoleRepository repository;

    @Test
    public void testFindAll() {
        List<RolePO> all = repository.findAll();
        Assert.assertNotEquals(0, all.size());
    }
}