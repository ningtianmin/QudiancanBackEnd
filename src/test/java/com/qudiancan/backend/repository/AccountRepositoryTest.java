package com.qudiancan.backend.repository;

import com.qudiancan.backend.BackEndApplicationTests;
import com.qudiancan.backend.pojo.po.AccountPO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author NINGTIANMIN
 */
@Transactional
public class AccountRepositoryTest extends BackEndApplicationTests {
    @Autowired
    private AccountRepository repository;

    @Test
    public void testFindAll() {
        List<AccountPO> all = repository.findAll();
    }
}