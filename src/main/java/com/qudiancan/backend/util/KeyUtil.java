package com.qudiancan.backend.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;

/**
 * @author NINGTIANMIN
 */
public class KeyUtil {
    private static final int ORDER_NUMBER_RANDOM_COUNT = 10;

    public static synchronized String generateOrderNumber() {
        return LocalDate.now().toString().replaceAll("-", "") +
                RandomStringUtils.randomNumeric(ORDER_NUMBER_RANDOM_COUNT);
    }
}
