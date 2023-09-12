package com.clever.bank.mapper;

import com.clever.bank.dao.impl.AccountDao;
import com.clever.bank.factory.FactoryDao;
import com.clever.bank.factory.FactoryGeneric;
import com.clever.bank.model.Account;
import com.clever.bank.model.Transaction;
import com.clever.bank.model.TransactionType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MapperTransaction {
    private static AccountDao accountDao = FactoryDao.getDaoInstance(AccountDao.class, FactoryGeneric.getUuidWrapper());


    public static Transaction getTransaction(ResultSet rs) throws SQLException {
        Account fromAccount = accountDao.findById(rs.getString(4)).orElse(new Account());
        Account toAccount = accountDao.findById(rs.getString(5)).orElse(new Account());

        Transaction transaction = new Transaction();
        transaction.setTransactionId(rs.getString(1));
        transaction.setTimestamp(rs.getTimestamp(2).toLocalDateTime());
        transaction.setTypeTransaction(TransactionType.valueOf(rs.getString(3)));
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(rs.getBigDecimal(6));

        return transaction;
    }
}
