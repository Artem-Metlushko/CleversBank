package com.clever.bank.dao;

import com.clever.bank.dao.impl.AccountDao;
import com.clever.bank.factory.FactoryGeneric;
import com.clever.bank.model.Account;
import com.clever.bank.util.ConnectionManager;
import com.clever.bank.util.TransactionManager;
import com.clever.bank.util.UuidWrapperMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.clever.bank.util.TestObjectUtil.expectedPersistedAccount;
import static com.clever.bank.util.TestObjectUtil.nonPersistedAccount;
import static org.junit.jupiter.api.Assertions.*;

class AccountDaoTest {

    private final UuidWrapperMock uuidWrapperMockAccount = UuidWrapperMock.getInstance("45c37890-2268-0010-aa93-a425be52eada");
    private final AccountDao accountDao = new AccountDao(uuidWrapperMockAccount, transactionManager);
    private static final TransactionManager transactionManager = FactoryGeneric.getTransactionManager();
    private Account savedAccount;
    public static final String CREATE_TABLE = """
            DROP TABLE IF EXISTS "account";
            DROP TABLE IF EXISTS "user";
            CREATE TABLE IF NOT EXISTS "user"
            (
                user_id     VARCHAR(255) PRIMARY KEY,
                firstName   VARCHAR(255) NOT NULL,
                lastName    VARCHAR(255) NOT NULL,
                surName     VARCHAR(255) NOT NULL,
                address     VARCHAR(255) NOT NULL,
                phoneNumber VARCHAR(255) NOT NULL
            ); 
            INSERT INTO "user" (user_id, firstName, lastName, surName, address, phoneNumber)
            VALUES ('45c39ef0-2268-0000-aa93-a425be52eada', 'Abba', 'Bara', 'John', 'Milan', '5554-12345');
            DROP TABLE IF EXISTS "bank";
            CREATE TABLE IF NOT EXISTS "bank"
            (
                bank_id VARCHAR(255) PRIMARY KEY,
                name    VARCHAR(255) NOT NULL
            );
            INSERT INTO "bank" (bank_id, name)
            VALUES ('45c39ef0-2268-1000-aa93-a425be52eada', 'Braavos');
            DROP TABLE IF EXISTS "account";
            CREATE TABLE IF NOT EXISTS "account"
            (
                account_id   VARCHAR(255) PRIMARY KEY,
                opening_date TIMESTAMP      NOT NULL,
                user_id      VARCHAR(255)   NOT NULL,
                bank_id      VARCHAR(255)   NOT NULL,
                balance      NUMERIC(18, 2) NOT NULL,
                version      BIGSERIAL      NOT NULL,
                FOREIGN KEY (bank_id) REFERENCES "bank" (bank_id) ON DELETE CASCADE,
                FOREIGN KEY (user_id) REFERENCES "user" (user_id) ON DELETE CASCADE
            );
            
            """;
    public static final String DROP_TABLE = """
            DROP TABLE IF EXISTS "account";
            DROP TABLE IF EXISTS "user";
            DROP TABLE IF EXISTS "bank";
            """;
    @BeforeEach
    void setUp() {

        createTable();
        transactionManager.beginTransaction();
        savedAccount = accountDao.save(nonPersistedAccount());
    }

    private void createTable() {
        try (Connection connection = ConnectionManager.get();
             Statement statement = connection.createStatement()
        ) {
            statement.execute(CREATE_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void dropTable() {
        try (Connection connection = ConnectionManager.get();
             Statement statement = connection.createStatement()
        ) {
            statement.execute(DROP_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
        transactionManager.rollback();
        dropTable();
    }

    @Test
    void save() {
        assertEquals(expectedPersistedAccount(), savedAccount);
    }

    @Test
    void update() {
        Account account = updateAccount();
        Account updatedAccount = accountDao.update(account);
        assertEquals(updatedAccount, account);
    }

    private Account updateAccount() {
        return Account.builder()
                .accountId(savedAccount.getAccountId())
                .openingDate(LocalDateTime.now())
                .user(savedAccount.getUser())
                .bank(savedAccount.getBank())
                .balance(new BigDecimal("2000.00"))
                .version(savedAccount.getVersion())
                .build();
    }

    @Test
    void findById() {

        Optional<Account> foundAccount = accountDao.findById(savedAccount.getAccountId());
        Account account = foundAccount.orElse(new Account());
        assertEquals(savedAccount, account);
    }

    @Test
    void deleteById() {
        assertTrue(accountDao.deleteById(savedAccount.getAccountId()));
        Optional<Account> deletedAccount = accountDao.findById(savedAccount.getAccountId());
        assertFalse(deletedAccount.isPresent());
    }


    @Test
    void findAll() {
        assertNotNull(accountDao.findAll());
    }
}
