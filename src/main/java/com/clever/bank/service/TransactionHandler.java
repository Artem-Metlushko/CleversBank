package com.clever.bank.service;

import com.clever.bank.model.Account;
import com.clever.bank.model.TransactionType;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.clever.bank.model.TransactionType.*;
import static java.math.BigDecimal.ZERO;

@Slf4j
public class TransactionHandler {
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final Lock lock = new ReentrantLock();

    public TransactionHandler(final AccountService accountService,
                              final TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;

    }

    public void handle(String transactionId) {

        if (lock.tryLock()) {
            var transaction = transactionService.findById(transactionId);
            var fromAccount = Optional.ofNullable(transaction.getFromAccount()).orElse(new Account());
            var toAccount = Optional.ofNullable(transaction.getToAccount()).orElse(new Account());
            var amount = transaction.getAmount();
            var transactionType = transaction.getTypeTransaction();

            try {
                if (transactionType == TRANSFER) {
                    checkingConditionalOfTransfer(amount, transactionType);

                    System.out.println(transactionService.findById(transactionId));
                    transferMoneyBetweenAccount(fromAccount, toAccount, amount);
                    System.out.println(transactionService.findById(transactionId));

                } else {


                    System.out.println(transactionService.findById(transactionId));
                    updateBalanceToAccount(fromAccount, amount, transactionType);
                    System.out.println(transactionService.findById(transactionId));
                }
            } finally {
                lock.unlock();
            }
        }
    }
    private void transferMoneyBetweenAccount(Account fromAccount,
                                             Account toAccount,
                                             BigDecimal amountForTransfer
    ) {
        var amountForTransferNegate = amountForTransfer.negate();
        if (updateBalanceToAccount(fromAccount, amountForTransferNegate, DEBIT)) {
            updateBalanceToAccount(toAccount, amountForTransfer, CREDIT);
        }
    }

    private boolean updateBalanceToAccount(Account account,BigDecimal amount,TransactionType transactionType
    ) {
        checkConditionalTransaction(amount, transactionType);

        var currentBalanceOfAccount = account.getBalance();

        var updatedBalance = currentBalanceOfAccount.add(amount);

        if(updatedBalance.compareTo(ZERO) > 0){
            account.setBalance(updatedBalance);
            accountService.update(account);
            return true;
        } return false;

    }

    private void checkConditionalTransaction(BigDecimal amount, TransactionType transactionType) {
        if (transactionType == DEBIT && amount.compareTo(ZERO) > 0) {
            log.info("If you want make a DEBIT, you should pass a negative amount");
            throw new RuntimeException();
        } else if (transactionType == CREDIT && amount.compareTo(ZERO) < 0) {
            log.info("If you want make a CREDIT, you should pass a positive amount");
            throw new RuntimeException();
        }
    }

    private void checkingConditionalOfTransfer(BigDecimal amountForTransfer, TransactionType transactionType) {
        if (transactionType == TRANSFER && amountForTransfer.compareTo(ZERO) < 0) {
            log.info("If you want make a TRANSFER, you should pass a positive amount");
            throw new RuntimeException();
        }
    }
}
