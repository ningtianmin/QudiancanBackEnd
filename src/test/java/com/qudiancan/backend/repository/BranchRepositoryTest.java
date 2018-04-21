package com.qudiancan.backend.repository;

import com.qudiancan.backend.BackEndApplicationTests;
import com.qudiancan.backend.pojo.po.BranchPO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author NINGTIANMIN
 */
@Transactional
public class BranchRepositoryTest extends BackEndApplicationTests {
    @Autowired
    private BranchRepository repository;

    @Test
    public void testFindAll() {
        List<BranchPO> all = repository.findAll();
    }
}