package com.clever.bank.dao.impl;

import com.clever.bank.dao.Dao;
import com.clever.bank.mapper.MapperUser;
import com.clever.bank.model.User;
import com.clever.bank.util.TransactionManager;
import com.clever.bank.util.UuidWrapper;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@Slf4j
public class UserDao implements Dao<String, User> {
    private final UuidWrapper uuidWrapper;
    private final TransactionManager tm;

    public UserDao(UuidWrapper uuidWrapper, TransactionManager tm) {
        this.uuidWrapper = uuidWrapper;
        this.tm = tm;
    }

    private static final String CREATE_USER = """
            INSERT INTO "user" (user_id, firstName, lastName, surName, address, phoneNumber)
            VALUES (?, ?, ?, ?, ?, ?);
            """;
    private static final String UPDATE_USER = """
            UPDATE "user"
            SET firstName = ?, lastName = ?, surName = ?, address = ?, phoneNumber = ?
            WHERE user_id = ?;
            """;
    private static final String FIND_BY_ID = "SELECT * FROM \"user\" WHERE user_id = ?";
    private static final String DELETE_USER = "DELETE FROM \"user\" WHERE user_id = ?";
    private static final String FIND_ALL = "SELECT * FROM \"user\"";

    @Override
    public User save(User user) {
        user.setUserId(uuidWrapper.randomUUID());
        var connection = tm.get();
        try (var ps = connection.prepareStatement(CREATE_USER, RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getSurName());
            ps.setString(5, user.getAddress());
            ps.setString(6, user.getPhoneNumber());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                user.setUserId(rs.getString(1));
            }

            log.info("User saved: {}", user.getUserId());
            return user;
        } catch (SQLException e) {
            log.error("Error saving user to database: {}", e.getMessage());
            throw new RuntimeException("Error saving user to database", e);
        }
    }

    @Override
    public User update(User user) {
        var connection = tm.get();
        try (var ps = connection.prepareStatement(UPDATE_USER, RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getSurName());
            ps.setString(4, user.getAddress());
            ps.setString(5, user.getPhoneNumber());
            ps.setString(6, user.getUserId());
            ps.executeUpdate();

            log.info("User updated: {}", user.getUserId());
            return user;
        } catch (SQLException e) {
            log.error("Error updating user in database: {}", e.getMessage());
            throw new RuntimeException("Error updating user in database", e);
        }
    }

    @Override
    public Optional<User> findById(String id) {
        var connection = tm.get();
        try (var ps = connection.prepareStatement(FIND_BY_ID)
        ) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = MapperUser.getUser(rs);
                log.info("User found: {}", id);
                return Optional.of(user);
            }
            log.info("User not found: {}", id);
        } catch (SQLException e) {
            log.error("Error fetching user from database: {}", e.getMessage());
            throw new RuntimeException("Error updating user in database", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(String id) {
        var connection = tm.get();
        try (var ps = connection.prepareStatement(DELETE_USER)
        ) {
            ps.setString(1, id);
            int rowsAffected = ps.executeUpdate();
            boolean isDeleted = rowsAffected > 0;
            if (isDeleted) {
                log.info("User deleted: {}", id);
            } else {
                log.info("User not found for deletion: {}", id);
            }
            return isDeleted;
        } catch (SQLException e) {
            log.error("Error deleting user from database: {}", e.getMessage());
            throw new RuntimeException("Error deleting user from database", e);
        }
    }

    @Override
    public List<User> findAll() {
        var connection = tm.get();
        try (var statement = connection.createStatement()
        ) {
            ResultSet rs = statement.executeQuery(FIND_ALL);
            List<User> userList = new ArrayList<>();
            while (rs.next()) {
                User user = MapperUser.getUser(rs);
                userList.add(user);
            }
            log.info("Found {} users", userList.size());
            return userList;
        } catch (SQLException e) {
            log.error("Error finding all users: {}", e.getMessage());
            throw new RuntimeException("Error finding all users", e);
        }
    }
}
