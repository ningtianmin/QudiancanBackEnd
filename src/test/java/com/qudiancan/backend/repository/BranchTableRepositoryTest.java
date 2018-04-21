package com.qudiancan.backend.repository;

import com.qudiancan.backend.BackEndApplicationTests;
import com.qudiancan.backend.pojo.po.BranchTablePO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author NINGTIANMIN
 */
@Transactional
public class BranchTableRepositoryTest extends BackEndApplicationTests {
    @Autowired
    private BranchTableRepository repository;

    @Test
    public void testFindAll() {
        List<BranchTablePO> all = repository.findAll();
    }
}