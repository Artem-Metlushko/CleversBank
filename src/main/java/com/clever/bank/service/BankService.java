package com.clever.bank.service;

import com.clever.bank.dao.impl.BankDao;
import com.clever.bank.model.Bank;
import com.clever.bank.util.TransactionManager;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class BankService {
    private final BankDao bankDao ;
    private final TransactionManager tm;

    public BankService(BankDao bankDao, TransactionManager tm) {
        this.bankDao = bankDao;
        this.tm = tm;
    }

    public Bank findById(String bankId) {
        try {
            tm.beginTransaction();
            Bank bank = bankDao.findById(bankId).orElse(new Bank());
            tm.closeTransaction();
            return bank;
        } catch (Exception e) {
            log.error("Error finding bank by ID: {}", e.getMessage());
            throw new RuntimeException("Error finding bank by ID", e);
        }
    }

    public Bank save(Bank bank) {
        try {
            tm.beginTransaction();
            Bank savedBank = bankDao.save(bank);
            tm.closeTransaction();
            return savedBank;
        } catch (Exception e) {
            log.error("Error saving bank: {}", e.getMessage());
            throw new RuntimeException("Error saving bank", e);
        }
    }

    public Bank update(Bank bank) {
        try {
            tm.beginTransaction();
            Bank updatedBank = bankDao.update(bank);
            tm.closeTransaction();
            return updatedBank;
        } catch (Exception e) {
            log.error("Error updating bank: {}", e.getMessage());
            throw new RuntimeException("Error updating bank", e);
        }
    }

    public void delete(Bank bank) {
        try {
            tm.beginTransaction();
            bankDao.deleteById(bank.getBankId());
            tm.closeTransaction();
        } catch (Exception e) {
            log.error("Error deleting bank: {}", e.getMessage());
            throw new RuntimeException("Error deleting bank", e);
        }
    }

    public List<Bank> findAll() {
        try {
            tm.beginTransaction();
            List<Bank> bankList = bankDao.findAll();
            tm.closeTransaction();
            return bankList;
        } catch (Exception e) {
            log.error("Error finding all banks: {}", e.getMessage());
            throw new RuntimeException("Error finding all banks", e);
        }
    }
}
