package com.qudiancan.backend.repository;

import com.qudiancan.backend.BackEndApplicationTests;
import com.qudiancan.backend.pojo.po.MemberPO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author NINGTIANMIN
 */
@Transactional
public class MemberRepositoryTest extends BackEndApplicationTests {
    @Autowired
    private MemberRepository repository;

    @Test
    public void testFindAll() {
        List<MemberPO> all = repository.findAll();
    }
}