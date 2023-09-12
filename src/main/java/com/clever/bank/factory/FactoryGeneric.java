package com.clever.bank.factory;

import com.clever.bank.service.AccountService;
import com.clever.bank.service.TransactionHandler;
import com.clever.bank.service.TransactionService;
import com.clever.bank.util.TransactionManager;
import com.clever.bank.util.UuidWrapper;

public class FactoryGeneric {

    private FactoryGeneric() {
        throw new IllegalStateException("Utility class");
    }

    private static TransactionHandler transactionManagement;

    private static UuidWrapper uuidWrapper;
    private static final AccountService accountService = FactoryService.getAccountService();
    private static final TransactionService transactionService = FactoryService.getTransactionService();

    private static TransactionManager transactionManager;

    public static TransactionHandler getTransactionHandler() {
        synchronized (TransactionHandler.class) {
            if (transactionManagement == null) {
                transactionManagement = new TransactionHandler(accountService, transactionService);
            }
        }
        return transactionManagement;
    }

    public static TransactionManager getTransactionManager() {
        synchronized (TransactionManager.class) {
            if (transactionManager == null) {
                transactionManager = new TransactionManager();
            }
        }
        return transactionManager;
    }

    public static UuidWrapper getUuidWrapper() {
        synchronized (UuidWrapper.class) {
            if (uuidWrapper == null) {
                uuidWrapper = new UuidWrapper();
            }
        }
        return uuidWrapper;
    }
}
