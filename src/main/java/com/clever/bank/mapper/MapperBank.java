package com.clever.bank.mapper;

import com.clever.bank.model.Bank;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MapperBank {
    public static Bank getBank(ResultSet rs) throws SQLException {
        Bank bank = new Bank();
        bank.setBankId(rs.getString(1));
        bank.setName(rs.getString(2));
        return bank;
    }
}
