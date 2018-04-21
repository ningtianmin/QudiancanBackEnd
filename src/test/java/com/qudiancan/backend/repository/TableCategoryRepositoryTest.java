package com.qudiancan.backend.repository;

import com.qudiancan.backend.BackEndApplicationTests;
import com.qudiancan.backend.pojo.po.TableCategoryPO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author NINGTIANMIN
 */
@Transactional
public class TableCategoryRepositoryTest extends BackEndApplicationTests {
    @Autowired
    private TableCategoryRepository repository;

    @Test
    public void testFindAll() {
        List<TableCategoryPO> all = repository.findAll();
    }
}