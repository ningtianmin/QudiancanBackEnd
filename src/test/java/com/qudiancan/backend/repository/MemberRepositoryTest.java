package com.qudiancan.backend.repository;

import com.qudiancan.backend.BackEndApplicationTests;
import com.qudiancan.backend.pojo.po.MemberPO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
public class MemberRepositoryTest extends BackEndApplicationTests {
    @Autowired
    private MemberRepository repository;

    @Test
    public void testFindAll() {
        List<MemberPO> all = repository.findAll();
        Assert.assertNotEquals(0, all.size());
    }
}