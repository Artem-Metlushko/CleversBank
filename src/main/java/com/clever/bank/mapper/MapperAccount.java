package com.clever.bank.mapper;

import com.clever.bank.dao.impl.BankDao;
import com.clever.bank.dao.impl.UserDao;
import com.clever.bank.factory.FactoryDao;
import com.clever.bank.factory.FactoryGeneric;
import com.clever.bank.model.Account;
import com.clever.bank.model.Bank;
import com.clever.bank.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MapperAccount {
    private static final UserDao userDao= FactoryDao.getDaoInstance(UserDao.class, FactoryGeneric.getUuidWrapper());
    private static final BankDao bankDao= FactoryDao.getDaoInstance(BankDao.class, FactoryGeneric.getUuidWrapper());

    public static Account getAccount(ResultSet rs) throws SQLException {

        User user = userDao.findById(rs.getString(3)).orElse(new User());
        Bank bank = bankDao.findById(rs.getString(4)).orElse(new Bank());
        Account account = new Account();
        account.setAccountId(rs.getString(1));
        account.setOpeningDate(rs.getTimestamp(2).toLocalDateTime());
        account.setUser(user);
        account.setBank(bank);
        account.setBalance(rs.getBigDecimal(5));
        account.setVersion(rs.getLong(6));
        return account;

    }
}
