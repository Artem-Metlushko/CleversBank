package com.clever.bank.dao;

import com.clever.bank.dao.impl.UserDao;
import com.clever.bank.factory.FactoryDao;
import com.clever.bank.factory.FactoryGeneric;
import com.clever.bank.model.User;

import com.clever.bank.util.*;
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


class UserDaoTest {
    private final UuidWrapperMock uuidWrapperMock = UuidWrapperMock.getInstance("45c39ef0-2268-0000-aa93-a425be52eada");

    private final static TransactionManager transactionManager = new TransactionManager();

    private final UserDao userDao = new UserDao(uuidWrapperMock, transactionManager);
    private User saveUser;
    private static final String CREATE_TABLE = """
            DROP TABLE IF EXISTS "transaction";
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
        saveUser = userDao.save(nonPersistedUser());

    }

    @AfterEach
    void tearDown() {
        transactionManager.rollback();
    }

    @Test
    void save() {
        assertEquals(expectedPersistedUser(), saveUser);

    }

    @Test
    void update() {
        User update = userDao.update(userForUpdate(saveUser));
        Optional<User> byId = userDao.findById(update.getUserId());
        User foundUserById = byId.orElse(new User());

        assertEquals(userForUpdate(saveUser), foundUserById);
    }


    @Test
    void findById() {
        Optional<User> byId = userDao.findById(saveUser.getUserId());
        User user = byId.orElse(new User());
        assertEquals(saveUser, user);
    }

    @Test
    void deleteById() {
        assertTrue(userDao.deleteById(saveUser.getUserId()));
    }

    @Test
    void findAll() {
        assertNotNull(userDao.findAll());
    }
}
