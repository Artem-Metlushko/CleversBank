package com.clever.bank.dao;

import com.clever.bank.dao.impl.BankDao;
import com.clever.bank.factory.FactoryDao;
import com.clever.bank.model.Bank;
import com.clever.bank.util.ConnectionManager;
import com.clever.bank.util.TestObjectUtil;
import com.clever.bank.util.TransactionManager;
import com.clever.bank.util.UuidWrapperMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static com.clever.bank.util.TestObjectUtil.*;
import static org.junit.jupiter.api.Assertions.*;

class BankDaoTest {
    private final UuidWrapperMock uuidWrapperMock = UuidWrapperMock.getInstance("45c39ef0-2268-1000-aa93-a425be52eada");

    private final static TransactionManager transactionManager = new TransactionManager();
    private final BankDao bankDao = new BankDao(uuidWrapperMock, transactionManager);
    private Bank saveBank;

    private static final String CREATE_TABLE = """
            DROP TABLE IF EXISTS "bank";
            CREATE TABLE IF NOT EXISTS "bank"
            (
                bank_id VARCHAR(255) PRIMARY KEY,
                name    VARCHAR(255) NOT NULL
            );
            """;


    @BeforeAll
    static void setUpAll() {
        try (Connection connection = ConnectionManager.get();
             Statement statement = connection.createStatement()
        ) {
            statement.execute(CREATE_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() {
        transactionManager.beginTransaction();
        saveBank = bankDao.save(nonPersistedBank());
    }

    @AfterEach
    void tearDown() {
        transactionManager.rollback();
    }

    @Test
    void save() {
        assertEquals(expectedPersistedBank(), saveBank);
    }

    @Test
    void update() {
        Bank update = bankDao.update(bankForUpdate(saveBank));
        Optional<Bank> byId = bankDao.findById(saveBank.getBankId());
        Bank bank = byId.orElse(new Bank());
        assertEquals(update, bank);
    }

    @Test
    void findById() {
        Optional<Bank> byId = bankDao.findById(saveBank.getBankId());
        Bank bank = byId.orElse(null);
        assertEquals(saveBank, bank);
    }

    @Test
    void deleteById() {
        assertTrue(bankDao.deleteById(saveBank.getBankId()));
    }

    @Test
    void findAll() {
        assertNotNull(bankDao.findAll());
    }
}
