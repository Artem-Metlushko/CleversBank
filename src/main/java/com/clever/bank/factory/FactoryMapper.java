package com.clever.bank.factory;

import com.clever.bank.mapper.MapperAccount;
import com.clever.bank.mapper.MapperTransaction;
import com.clever.bank.service.AccountService;
import com.clever.bank.service.BankService;
import com.clever.bank.service.UserService;

public class FactoryMapper {

    private static MapperAccount mapperAccount ;

    private static final UserService userService = FactoryService.getUserService();
    private static final BankService bankService = FactoryService.getBankService();

    private static final AccountService accountService = FactoryService.getAccountService();

    private static MapperTransaction mapperTransaction ;

    public static MapperAccount getMapperAccount() {
        synchronized (MapperAccount.class) {
            if (mapperAccount == null) {
                mapperAccount = new MapperAccount();
            }
        }
        return mapperAccount;
    }

    public static MapperTransaction getMapperTransaction() {
        synchronized (MapperTransaction.class) {
            if (mapperTransaction == null) {
                mapperTransaction = new MapperTransaction();
            }
        }
        return mapperTransaction;
    }
}
