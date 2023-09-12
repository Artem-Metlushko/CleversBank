package com.clever.bank.service;

import com.clever.bank.dao.impl.AccountDao;
import com.clever.bank.model.Account;
import com.clever.bank.util.TransactionManager;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Slf4j
public class AccountService {
    private final AccountDao accountDao ;
    private final TransactionManager tm;

    public AccountService(AccountDao accountDao, TransactionManager tm) {
        this.accountDao = accountDao;
        this.tm = tm;
    }

    public Account findById(String accountId) {
        try {
            tm.beginTransaction();
            Account account = accountDao.findById(accountId).orElse(new Account());
            tm.closeTransaction();
            return account;
        } catch (Exception e) {
            log.error("Error finding account by ID: {}", e.getMessage());
            throw new RuntimeException("Error finding account by ID", e);
        }
    }

    public Account save(Account account) {
        try {
            tm.beginTransaction();
            Account savedAccount = accountDao.save(account);
            tm.closeTransaction();
            return savedAccount;
        } catch (Exception e) {
            log.error("Error saving account: {}", e.getMessage());
            throw new RuntimeException("Error saving account", e);
        }
    }

    public Account update(Account account) {
        try {
            tm.beginTransaction();
            Account updatedAccount = accountDao.update(account);
            tm.closeTransaction();
            return updatedAccount;
        } catch (Exception e) {
            log.error("Error updating account: {}", e.getMessage());
            throw new RuntimeException("Error updating account", e);
        }
    }

    public void delete(Account account) {
        try {
            tm.beginTransaction();
            accountDao.deleteById(account.getAccountId());
            tm.closeTransaction();
        } catch (Exception e) {
            log.error("Error deleting account: {}", e.getMessage());
            throw new RuntimeException("Error deleting account", e);
        }
    }

    public List<Account> findAll() {
        try {
            tm.beginTransaction();
            List<Account> accountList = accountDao.findAll();
            tm.closeTransaction();
            return accountList;
        } catch (Exception e) {
            log.error("Error finding all accounts: {}", e.getMessage());
            throw new RuntimeException("Error finding all accounts", e);
        }
    }
}
