package com.qudiancan.backend.repository;

import com.qudiancan.backend.BackEndApplicationTests;
import com.qudiancan.backend.pojo.po.BranchProductPO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author NINGTIANMIN
 */
@Transactional
public class BranchProductRepositoryTest extends BackEndApplicationTests {
    @Autowired
    private BranchProductRepository repository;

    @Test
    public void testFindAll() {
        List<BranchProductPO> all = repository.findAll();
    }
}