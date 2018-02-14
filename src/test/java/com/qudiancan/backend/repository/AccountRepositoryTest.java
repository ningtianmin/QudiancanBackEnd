package com.qudiancan.backend.repository;

import com.qudiancan.backend.BackEndApplicationTests;
import com.qudiancan.backend.pojo.po.AccountPO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public class AccountRepositoryTest extends BackEndApplicationTests {
    @Autowired
    private AccountRepository repository;

    @Test
    public void testFindAll() {
        List<AccountPO> all = repository.findAll();
        Assert.assertNotEquals(0, all.size());
    }
}