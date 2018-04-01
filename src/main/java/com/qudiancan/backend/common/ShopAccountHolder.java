package com.qudiancan.backend.common;

import com.qudiancan.backend.pojo.po.AccountPO;

/**
 * @author NINGTIANMIN
 */
public class ShopAccountHolder {
    private static final ThreadLocal<AccountPO> ACCOUNT_THREAD_LOCAL = new ThreadLocal<>();

    public static AccountPO get() {
        return ACCOUNT_THREAD_LOCAL.get();
    }

    public static void set(AccountPO accountPO) {
        ACCOUNT_THREAD_LOCAL.set(accountPO);
    }

    public static void remove() {
        ACCOUNT_THREAD_LOCAL.remove();
    }
}
