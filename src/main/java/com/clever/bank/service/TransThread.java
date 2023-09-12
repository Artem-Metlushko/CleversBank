package com.clever.bank.service;

import com.clever.bank.model.Transaction;

public class TransThread extends Thread {
    private final TransactionHandler transactionManagement;

    private final Transaction transaction;

    public TransThread(TransactionHandler transactionHandler, Transaction transaction) {
        this.transactionManagement = transactionHandler;
        this.transaction = transaction;
    }

    @Override
    public void run() {

        for (int i = 0; i < 5; i++) {
            transactionManagement.handle(transaction.getTransactionId());
        }

    }
}
