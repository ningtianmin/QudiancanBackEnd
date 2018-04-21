package com.qudiancan.backend.controller.merchant;

import com.qudiancan.backend.common.ShopAccountHolder;
import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.service.shop.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author NINGTIANMIN
 */
@Controller
@RequestMapping("/merchants")
public class MerchantStatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/orderStatistics")
    public ModelAndView orderStatistics() {
        return new ModelAndView("merchants/data/orderStatistics");
    }

    @GetMapping("/orderStatisticsHour")
    public ModelAndView orderStatisticsHour(@RequestParam Integer branchId, @RequestParam String date) {
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "时间格式有误");
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("orderStatisticsList", statisticsService.orderStatisticsHour(ShopAccountHolder.get().getId(), branchId, localDate));
        return new ModelAndView("merchants/data/orderStatisticsHour", map);
    }

    @GetMapping("/orderStatisticsDay")
    public ModelAndView orderStatisticsDay(@RequestParam Integer branchId, @RequestParam String date) {
        date += "-01";
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "时间格式有误");
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("orderStatisticsList", statisticsService.orderStatisticsDay(ShopAccountHolder.get().getId(), branchId, localDate));
        return new ModelAndView("merchants/data/orderStatisticsDay", map);
    }

    @GetMapping("/tableStatistics")
    public ModelAndView tableStatistics() {
        return new ModelAndView("merchants/data/tableStatistics");
    }

    @GetMapping("/tableStatisticsPeriod")
    public ModelAndView tableStatisticsPeriod(@RequestParam Integer branchId, @RequestParam String datePeriod) {
        LocalDate startDate, endDate;
        try {
            startDate = LocalDate.parse(datePeriod.substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            endDate = LocalDate.parse(datePeriod.substring(13), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "时间格式有误");
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("tableStatisticsList", statisticsService.tableStatisticsPeriod(ShopAccountHolder.get().getId(), branchId, startDate, endDate));
        return new ModelAndView("merchants/data/tableStatisticsPeriod", map);
    }
}
