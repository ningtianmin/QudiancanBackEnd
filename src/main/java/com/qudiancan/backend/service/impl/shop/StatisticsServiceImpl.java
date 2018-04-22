package com.qudiancan.backend.service.impl.shop;

import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.enums.shop.ShopIsCreator;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.dto.shop.OrderStatistics;
import com.qudiancan.backend.pojo.dto.shop.TableStatistics;
import com.qudiancan.backend.pojo.po.AccountPO;
import com.qudiancan.backend.pojo.po.BranchOrderPO;
import com.qudiancan.backend.repository.AccountRepository;
import com.qudiancan.backend.repository.BranchOrderRepository;
import com.qudiancan.backend.repository.BranchRepository;
import com.qudiancan.backend.service.shop.ShopBranchService;
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
import java.util.stream.Collectors;

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
    @Autowired
    private ShopBranchService shopBranchService;

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

    @Override
    public List<TableStatistics> tableStatisticsPeriod(int accountId, int branchId, LocalDate startDate, LocalDate endDate) {
        log.info("[桌台统计（时段）]accountId:{},branchId:{},startDate:{},endDate:{}", accountId, branchId, startDate, endDate);
        if (Objects.isNull(startDate) || Objects.isNull(endDate)) {
            throw new ShopException(ResponseEnum.PARAM_INCOMPLETE);
        }
        if (startDate.isAfter(endDate)) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "开始时间应小于结束时间");
        }
        AccountPO accountPO = accountRepository.findOne(accountId);
        if (Objects.isNull(accountPO)) {
            throw new ShopException(ResponseEnum.PARAM_INVALID, "accountId");
        }
        if (!shopBranchService.canManageBranch(accountId, accountPO.getShopId(), branchId)) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        List<BranchOrderPO> branchOrders = branchOrderRepository.findByBranchIdAndCreateTimeBetween(branchId, Timestamp.valueOf(LocalDateTime.of(startDate, LocalTime.of(0, 0, 0))),
                Timestamp.valueOf(LocalDateTime.of(endDate, LocalTime.of(23, 59, 59))));
        branchOrders = branchOrders.stream().filter(o -> Objects.nonNull(o.getTableId())).collect(Collectors.toList());
        Map<Integer, List<BranchOrderPO>> branchOrderMap = branchOrders.stream().collect(Collectors.groupingBy(BranchOrderPO::getTableId));
        List<TableStatistics> tableStatisticsList = new ArrayList<>(branchOrderMap.size() + 1);
        int totOrderNum = 0, totCustomerNum = 0;
        BigDecimal totChargeSum = BigDecimal.ZERO;
        for (Map.Entry<Integer, List<BranchOrderPO>> entry : branchOrderMap.entrySet()) {
            List<BranchOrderPO> list = entry.getValue();
            int orderNum = list.size(), customerNum = 0;
            totOrderNum += list.size();
            BigDecimal chargeSum = BigDecimal.ZERO;
            for (BranchOrderPO branchOrderPO : list) {
                customerNum += branchOrderPO.getCustomerNum();
                totCustomerNum += branchOrderPO.getCustomerNum();
                chargeSum = chargeSum.add(branchOrderPO.getChargeSum());
                totChargeSum = totChargeSum.add(branchOrderPO.getChargeSum());
            }
            tableStatisticsList.add(new TableStatistics(list.get(0).getTableName(), orderNum, customerNum, chargeSum,
                    chargeSum.divide(new BigDecimal(orderNum), 2), chargeSum.divide(new BigDecimal(customerNum), 2)));
        }
        tableStatisticsList.add(new TableStatistics("合计", totOrderNum, totCustomerNum, totChargeSum,
                totOrderNum == 0 ? BigDecimal.ZERO : totChargeSum.divide(new BigDecimal(totOrderNum), 2), totCustomerNum == 0 ? BigDecimal.ZERO : totChargeSum.divide(new BigDecimal(totCustomerNum), 2)));
        return tableStatisticsList;
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
