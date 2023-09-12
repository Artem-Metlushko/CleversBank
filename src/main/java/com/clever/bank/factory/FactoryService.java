package com.clever.bank.factory;

import com.clever.bank.dao.impl.AccountDao;
import com.clever.bank.dao.impl.BankDao;
import com.clever.bank.dao.impl.TransactionDao;
import com.clever.bank.dao.impl.UserDao;
import com.clever.bank.service.AccountService;
import com.clever.bank.service.BankService;
import com.clever.bank.service.TransactionService;
import com.clever.bank.service.UserService;
import com.clever.bank.util.TransactionManager;

public class FactoryService {

    private FactoryService() {
        throw new IllegalStateException("Utility class");
    }

    private static AccountService accountService;
    private static UserService userService;
    private static BankService bankService;
    private static TransactionService transactionService;

    private static final AccountDao accountDao = FactoryDao.getDaoInstance(AccountDao.class, FactoryGeneric.getUuidWrapper());
    public static final BankDao bankDao = FactoryDao.getDaoInstance(BankDao.class, FactoryGeneric.getUuidWrapper());
    public static final TransactionDao transactionDao = FactoryDao.getDaoInstance(TransactionDao.class, FactoryGeneric.getUuidWrapper());
    public static final UserDao userDao = FactoryDao.getDaoInstance(UserDao.class, FactoryGeneric.getUuidWrapper());
    private static final TransactionManager tm = FactoryGeneric.getTransactionManager();

    public static AccountService getAccountService() {
        synchronized (AccountService.class) {
            if (accountService == null) {
                accountService = new AccountService(accountDao, tm);
            }
        }
        return accountService;
    }

    public static UserService getUserService() {
        synchronized (UserService.class) {
            if (userService == null) {
                userService = new UserService(userDao, tm);
            }
        }
        return userService;
    }

    public static BankService getBankService() {
        synchronized (BankService.class) {
            if (bankService == null) {
                bankService = new BankService(bankDao, tm);
            }
        }
        return bankService;
    }

    public static TransactionService getTransactionService() {
        synchronized (TransactionService.class) {
            if (transactionService == null) {
                transactionService = new TransactionService(transactionDao, tm);
            }
        }
        return transactionService;
    }



}

