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
public class Account {
    private String accountId;
    private LocalDateTime openingDate;
    private User user;
    private Bank bank;
    private BigDecimal balance;
    private Long version;
}

