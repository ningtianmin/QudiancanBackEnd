package com.qudiancan.backend.repository;

import com.qudiancan.backend.BackEndApplicationTests;
import com.qudiancan.backend.pojo.po.PaysapiPO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author NINGTIANMIN
 */
@Transactional
public class PaysapiRepositoryTest extends BackEndApplicationTests {
    @Autowired
    private PaysapiRepository repository;

    @Test
    public void testFindAll() {
        List<PaysapiPO> all = repository.findAll();
    }
}