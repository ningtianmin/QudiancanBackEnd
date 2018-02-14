package com.qudiancan.backend.repository;

import com.qudiancan.backend.BackEndApplicationTests;
import com.qudiancan.backend.pojo.po.BranchPO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public class BranchRepositoryTest extends BackEndApplicationTests {
    @Autowired
    private BranchRepository repository;

    @Test
    public void testFindAll() {
        List<BranchPO> all = repository.findAll();
        Assert.assertNotEquals(0, all.size());
    }
}