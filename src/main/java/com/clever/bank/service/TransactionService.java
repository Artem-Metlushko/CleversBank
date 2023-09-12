package com.clever.bank.service;

import com.clever.bank.dao.impl.TransactionDao;
import com.clever.bank.model.Transaction;
import com.clever.bank.util.TransactionManager;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TransactionService {
    private final TransactionDao transactionDao;
    private final TransactionManager tm;

    public TransactionService(TransactionDao transactionDao, TransactionManager tm) {
        this.transactionDao = transactionDao;
        this.tm = tm;
    }


    public Transaction findById(String transactionId) {
        try {
            tm.beginTransaction();
            Transaction transaction = transactionDao.findById(transactionId).orElse(new Transaction());
            tm.closeTransaction();
            return transaction;
        } catch (Exception e) {
            log.error("Error finding transaction by ID: {}", e.getMessage());
            throw new RuntimeException("Error finding transaction by ID", e);
        }
    }

    public Transaction save(Transaction transaction) {
        try {
            tm.beginTransaction();
            Transaction savedTransaction = transactionDao.save(transaction);
            tm.closeTransaction();
            return savedTransaction;
        } catch (Exception e) {
            log.error("Error saving transaction: {}", e.getMessage());
            throw new RuntimeException("Error saving transaction", e);
        }
    }

    public void delete(Transaction transaction) {
        try {
            tm.beginTransaction();
            transactionDao.deleteById(transaction.getTransactionId());
            tm.closeTransaction();
        } catch (Exception e) {
            log.error("Error deleting transaction: {}", e.getMessage());
            throw new RuntimeException("Error deleting transaction", e);
        }
    }

    public List<Transaction> findAll() {
        try {
            tm.beginTransaction();
            List<Transaction> transactionList = transactionDao.findAll();
            tm.closeTransaction();
            return transactionList;
        } catch (Exception e) {
            log.error("Error finding all transactions: {}", e.getMessage());
            throw new RuntimeException("Error finding all transactions", e);
        }
    }
}
