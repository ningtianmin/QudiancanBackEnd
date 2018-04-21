package com.qudiancan.backend.repository;

import com.qudiancan.backend.BackEndApplicationTests;
import com.qudiancan.backend.pojo.po.CartPO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author NINGTIANMIN
 */
@Transactional
public class CartRepositoryTest extends BackEndApplicationTests {
    @Autowired
    private CartRepository repository;

    @Test
    public void testFindAll() {
        List<CartPO> all = repository.findAll();
    }
}