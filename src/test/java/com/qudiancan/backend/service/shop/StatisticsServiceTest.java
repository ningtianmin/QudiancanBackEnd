package com.qudiancan.backend.service.shop;

import com.qudiancan.backend.BackEndApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

/**
 * @author NINGTIANMIN
 */
public class StatisticsServiceTest extends BackEndApplicationTests {
    @Autowired
    private StatisticsService statisticsService;

    @Test
    public void testOrderStatisticsHour() {
        statisticsService.orderStatisticsHour(1, 1, LocalDate.of(2018, 4, 20));
    }

    @Test
    public void testOrderStatisticsDay() {
        statisticsService.orderStatisticsDay(1, 1, LocalDate.of(2018, 4, 1));
    }
}