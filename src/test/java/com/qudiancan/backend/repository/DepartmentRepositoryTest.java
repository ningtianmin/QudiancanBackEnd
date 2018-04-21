package com.qudiancan.backend.repository;

import com.qudiancan.backend.BackEndApplicationTests;
import com.qudiancan.backend.pojo.po.DepartmentPO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author NINGTIANMIN
 */
@Transactional
public class DepartmentRepositoryTest extends BackEndApplicationTests {
    @Autowired
    private DepartmentRepository repository;

    @Test
    public void testFindAll() {
        List<DepartmentPO> all = repository.findAll();
    }
}