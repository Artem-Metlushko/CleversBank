package com.clever.bank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    private String transactionId;
    private LocalDateTime timestamp;
    private TransactionType typeTransaction;
    private Account fromAccount;
    private Account toAccount;
    private BigDecimal amount;

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", fromAccount=" + fromAccount.getBalance() +
                ", toAccount=" + toAccount.getBalance() +
                ", amount=" + amount +
                '}';
    }
}

