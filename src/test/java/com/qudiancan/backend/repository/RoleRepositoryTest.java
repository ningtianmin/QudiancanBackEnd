package com.qudiancan.backend.repository;

import com.qudiancan.backend.BackEndApplicationTests;
import com.qudiancan.backend.pojo.po.RolePO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author NINGTIANMIN
 */
@Transactional
public class RoleRepositoryTest extends BackEndApplicationTests {
    @Autowired
    private RoleRepository repository;

    @Test
    public void testFindAll() {
        List<RolePO> all = repository.findAll();
    }
}