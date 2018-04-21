package com.qudiancan.backend.repository;

import com.qudiancan.backend.BackEndApplicationTests;
import com.qudiancan.backend.pojo.po.AuthorityPO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author NINGTIANMIN
 */
@Transactional
public class AuthorityRepositoryTest extends BackEndApplicationTests {
    @Autowired
    private AuthorityRepository repository;

    @Test
    public void testFindAll() {
        List<AuthorityPO> all = repository.findAll();
    }
}