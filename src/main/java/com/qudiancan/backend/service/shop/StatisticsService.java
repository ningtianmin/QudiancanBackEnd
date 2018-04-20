package com.qudiancan.backend.service.shop;

import com.qudiancan.backend.pojo.dto.shop.OrderStatistics;

import java.time.LocalDate;
import java.util.List;

/**
 * @author NINGTIANMIN
 */
public interface StatisticsService {
    /**
     * 订单统计（时）
     *
     * @param accountId 账户id
     * @param branchId  门店id
     * @param date      时间（年月日）
     * @return 订单统计列表
     */
    List<OrderStatistics> orderStatisticsHour(int accountId, int branchId, LocalDate date);

    /**
     * 订单统计（日）
     *
     * @param accountId 账户id
     * @param branchId  门店id
     * @param date      时间（年月）
     * @return 订单统计列表
     */
    List<OrderStatistics> orderStatisticsDay(int accountId, int branchId, LocalDate date);
}
