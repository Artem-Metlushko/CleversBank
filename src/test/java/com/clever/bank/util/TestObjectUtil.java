package com.clever.bank.util;

import com.clever.bank.model.Account;
import com.clever.bank.model.Bank;
import com.clever.bank.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestObjectUtil {

    public static User expectedPersistedUser() {
        return User.builder()
                .userId("45c39ef0-2268-0000-aa93-a425be52eada")
                .firstName("Abba")
                .lastName("Bara")
                .surName("John")
                .address("Milan")
                .phoneNumber("5554-12345")
                .build();
    }

    public static User nonPersistedUser() {
        return User.builder()
                .firstName("Abba")
                .lastName("Bara")
                .surName("John")
                .address("Milan")
                .phoneNumber("5554-12345")
                .build();
    }

    public static User userForUpdate(User saveUser) {
        return User.builder()
                .userId(saveUser.getUserId())
                .firstName("Jonni")
                .lastName("Hedrix")
                .surName("Depp")
                .address("California")
                .phoneNumber("5554-12345")
                .build();
    }
    public static Bank expectedPersistedBank() {
        return Bank.builder()
                .bankId("45c39ef0-2268-1000-aa93-a425be52eada")
                .name("Braavos")
                .build();
    }
    public static Bank nonPersistedBank() {
        return Bank.builder()
                .name("Braavos")
                .build();
    }

    public static Bank bankForUpdate(Bank saveBank) {
        return Bank.builder()
                .bankId(saveBank.getBankId())
                .name("BelarusBank")
                .build();
    }

    public static Account expectedPersistedAccount() {
        User user = expectedPersistedUser();
        Bank bank = expectedPersistedBank();
        return Account.builder()
                .accountId("45c37890-2268-0010-aa93-a425be52eada")
                .openingDate(LocalDateTime.of(2020, 1, 1, 0, 0))
                .user(user)
                .bank(bank)
                .balance(new BigDecimal("1000.00"))
                .version(1L)
                .build();

    }

    public static Account nonPersistedAccount() {
        User user = expectedPersistedUser();
        Bank bank = expectedPersistedBank();
        return Account.builder()
                .user(user)
                .openingDate(LocalDateTime.of(2020, 1, 1, 0, 0))
                .bank(bank)
                .balance(new BigDecimal("1000.00"))
                .version(1L)
                .build();
    }

}
