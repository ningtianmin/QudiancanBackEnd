package com.qudiancan.backend.repository;

import com.qudiancan.backend.BackEndApplicationTests;
import com.qudiancan.backend.pojo.po.PaysapiPO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public class PaysapiRepositoryTest extends BackEndApplicationTests {
    @Autowired
    private PaysapiRepository repository;

    @Test
    public void testFindAll() {
        List<PaysapiPO> all = repository.findAll();
        Assert.assertNotEquals(0, all.size());
    }
}