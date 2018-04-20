package com.qudiancan.backend.service.impl.shop;

import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.enums.shop.ShopIsCreator;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.dto.shop.OrderStatistics;
import com.qudiancan.backend.pojo.po.AccountPO;
import com.qudiancan.backend.pojo.po.BranchOrderPO;
import com.qudiancan.backend.repository.AccountRepository;
import com.qudiancan.backend.repository.BranchOrderRepository;
import com.qudiancan.backend.repository.BranchRepository;
import com.qudiancan.backend.service.shop.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.*;

/**
 * @author NINGTIANMIN
 */
@Service
@Slf4j
public class StatisticsServiceImpl implements StatisticsService {
    public static final String COMMA = ",";
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private BranchOrderRepository branchOrderRepository;

    @Override
    public List<OrderStatistics> orderStatisticsHour(int accountId, int branchId, LocalDate date) {
        log.info("[订单统计（时）]accountId:{},branchId:{},date:{}", accountId, branchId, date);
        AccountPO accountPO = accountRepository.findOne(accountId);
        if (Objects.isNull(accountPO)) {
            throw new ShopException(ResponseEnum.PARAM_INVALID, "accountId");
        }
        if (!ShopIsCreator.YES.getKey().equals(accountPO.getIsCreator()) && Arrays.stream(accountPO.getBranchIds().split(COMMA)).noneMatch(o -> o.equals(branchId + ""))) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }

        List<BranchOrderPO> branchOrders = branchOrderRepository.findByBranchIdAndCreateTimeBetween(branchId, Timestamp.valueOf(LocalDateTime.of(date, LocalTime.of(0, 0, 0))), Timestamp.valueOf(LocalDateTime.of(date, LocalTime.of(23, 59, 59))));
        List<OrderStatistics> orderStatisticsList = new ArrayList<>(25);
        int hourSize = 24;
        for (int i = 0; i < hourSize; i++) {
            String period = "" + (i < 10 ? "0" + i : i) + ":00 - " + ((i + 1) < 10 ? "0" + (i + 1) : (i + 1)) + ":00";
            orderStatisticsList.add(new OrderStatistics(period, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 0, 0, BigDecimal.ZERO, BigDecimal.ZERO));
        }
        branchOrders.forEach(o -> {
            LocalDateTime localDateTime = o.getCreateTime().toLocalDateTime();
            OrderStatistics orderStatistics = orderStatisticsList.get(localDateTime.getHour());
            orderStatistics.setChargeSum(orderStatistics.getChargeSum().add(o.getChargeSum()));
            orderStatistics.setCustomerNum(orderStatistics.getCustomerNum() + o.getCustomerNum());
            orderStatistics.setDiscountSum(orderStatistics.getDiscountSum().add(o.getDiscountSum()));
            orderStatistics.setOrderNum(orderStatistics.getOrderNum() + 1);
            orderStatistics.setTotalSum(orderStatistics.getTotalSum().add(o.getTotalSum()));
            orderStatistics.setWipeSum(orderStatistics.getWipeSum().add(o.getWipeSum()));
        });
        tot(orderStatisticsList);
        return orderStatisticsList;
    }

    @Override
    public List<OrderStatistics> orderStatisticsDay(int accountId, int branchId, LocalDate date) {
        log.info("[订单统计（日）]accountId:{},branchId:{},date:{}", accountId, branchId, date);
        int year = date.getYear();
        int month = date.getMonthValue();
        date = LocalDate.of(year, month, 1);

        String monthStr = (month < 10 ? "0" : "") + month;
        AccountPO accountPO = accountRepository.findOne(accountId);
        if (Objects.isNull(accountPO)) {
            throw new ShopException(ResponseEnum.PARAM_INVALID, "accountId");
        }
        if (!ShopIsCreator.YES.getKey().equals(accountPO.getIsCreator()) && Arrays.stream(accountPO.getBranchIds().split(COMMA)).noneMatch(o -> o.equals(branchId + ""))) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }

        List<BranchOrderPO> branchOrders = branchOrderRepository.findByBranchIdAndCreateTimeBetween(branchId,
                Timestamp.valueOf(LocalDateTime.of(date, LocalTime.of(0, 0, 0))),
                Timestamp.valueOf(LocalDateTime.of(year, month, date.lengthOfMonth(), 23, 59, 59)));
        List<OrderStatistics> orderStatisticsList = new ArrayList<>(date.lengthOfMonth() + 1);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0, size = date.lengthOfMonth(); i < size; i++) {
            String period = stringBuilder.append(monthStr).append("-").append(i < 9 ? "0" + (i + 1) : (i + 1))
                    .append("(").append(date.plusDays(i).getDayOfWeek().getDisplayName(TextStyle.FULL_STANDALONE, Locale.CHINESE))
                    .append(")").toString();
            stringBuilder.delete(0, stringBuilder.length());
            orderStatisticsList.add(new OrderStatistics(period, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 0, 0, BigDecimal.ZERO, BigDecimal.ZERO));
        }
        branchOrders.forEach(o -> {
            LocalDateTime localDateTime = o.getCreateTime().toLocalDateTime();
            OrderStatistics orderStatistics = orderStatisticsList.get(localDateTime.getDayOfMonth() - 1);
            orderStatistics.setChargeSum(orderStatistics.getChargeSum().add(o.getChargeSum()));
            orderStatistics.setCustomerNum(orderStatistics.getCustomerNum() + o.getCustomerNum());
            orderStatistics.setDiscountSum(orderStatistics.getDiscountSum().add(o.getDiscountSum()));
            orderStatistics.setOrderNum(orderStatistics.getOrderNum() + 1);
            orderStatistics.setTotalSum(orderStatistics.getTotalSum().add(o.getTotalSum()));
            orderStatistics.setWipeSum(orderStatistics.getWipeSum().add(o.getWipeSum()));
        });
        tot(orderStatisticsList);
        return orderStatisticsList;
    }

    private void tot(List<OrderStatistics> orderStatisticsList) {
        BigDecimal totTotalSum = BigDecimal.ZERO;
        BigDecimal totDiscountSum = BigDecimal.ZERO;
        BigDecimal totWipeSum = BigDecimal.ZERO;
        BigDecimal totChargeSum = BigDecimal.ZERO;
        BigDecimal totOrderNum = BigDecimal.ZERO;
        BigDecimal totCustomerNum = BigDecimal.ZERO;
        for (OrderStatistics orderStatistics : orderStatisticsList) {
            totTotalSum = totTotalSum.add(orderStatistics.getTotalSum());
            totDiscountSum = totDiscountSum.add(orderStatistics.getDiscountSum());
            totWipeSum = totWipeSum.add(orderStatistics.getWipeSum());
            totChargeSum = totChargeSum.add(orderStatistics.getChargeSum());
            totOrderNum = totOrderNum.add(new BigDecimal(orderStatistics.getOrderNum()));
            totCustomerNum = totCustomerNum.add(new BigDecimal(orderStatistics.getCustomerNum()));

            if (orderStatistics.getCustomerNum() != 0) {
                orderStatistics.setAverageCustomer(orderStatistics.getChargeSum().divide(new BigDecimal(orderStatistics.getCustomerNum()), 2));
            }
            if (orderStatistics.getCustomerNum() != 0) {
                orderStatistics.setAverageOrder(orderStatistics.getChargeSum().divide(new BigDecimal(orderStatistics.getOrderNum()), 2));
            }
        }
        orderStatisticsList.add(new OrderStatistics("合计", totTotalSum, totDiscountSum, totWipeSum, totChargeSum, totOrderNum.intValue(), totCustomerNum.intValue(),
                totOrderNum.intValue() != 0 ? totChargeSum.divide(totOrderNum, 2) : BigDecimal.ZERO,
                totCustomerNum.intValue() != 0 ? totChargeSum.divide(totCustomerNum, 2) : BigDecimal.ZERO));
    }
}
