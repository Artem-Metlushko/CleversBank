package com.clever.bank.factory;

import com.clever.bank.dao.Dao;
import com.clever.bank.dao.impl.AccountDao;
import com.clever.bank.dao.impl.BankDao;
import com.clever.bank.dao.impl.TransactionDao;
import com.clever.bank.dao.impl.UserDao;
import com.clever.bank.util.TransactionManager;
import com.clever.bank.util.UuidWrapper;

public class FactoryDao {

    private FactoryDao() {

    }
    private static UserDao userDao;
    private static BankDao bankDao;
    private static AccountDao accountDao;
    private static TransactionDao transactionDao;

    private static final UuidWrapper uuidWrapper = FactoryGeneric.getUuidWrapper();
    private static final TransactionManager tm = FactoryGeneric.getTransactionManager();



    private static UserDao getUserDao(UuidWrapper uuidWrapper) {
        synchronized (UserDao.class) {
            if (userDao == null) {
                userDao = new UserDao(uuidWrapper, tm);
            }
        }
        return userDao;
    }

    private static BankDao getBankDao(UuidWrapper uuidWrapper) {
        synchronized (BankDao.class) {
            if (bankDao == null) {
                bankDao = new BankDao(uuidWrapper, tm);
            }
        }
        return bankDao;
    }
    private static AccountDao getAccountDao() {
        synchronized (UserDao.class) {
            if (accountDao == null) {
                accountDao = new AccountDao(uuidWrapper, tm);
            }
        }
        return accountDao;
    }
    private static TransactionDao getTransactionDao() {
        synchronized (TransactionDao.class) {
            if (transactionDao == null) {
                transactionDao = new TransactionDao(uuidWrapper, tm);
            }
        }
        return transactionDao;
    }

    public static <T extends Dao> T getDaoInstance(Class<T> daoClass,UuidWrapper uuidWrapper) {
        if(daoClass == UserDao.class){
            return daoClass.cast(getUserDao(uuidWrapper));
        } else if (daoClass == BankDao.class) {
            return daoClass.cast(getBankDao(uuidWrapper));
        }else if (daoClass == AccountDao.class) {
            return daoClass.cast(getAccountDao());
        } else if (daoClass == TransactionDao.class) {
            return daoClass.cast(getTransactionDao());
        }
        return null;
    }


}



