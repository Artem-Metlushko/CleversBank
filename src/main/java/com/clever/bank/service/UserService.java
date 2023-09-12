package com.clever.bank.service;

import com.clever.bank.dao.impl.UserDao;
import com.clever.bank.model.User;
import com.clever.bank.util.TransactionManager;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Slf4j
public class UserService {
    private final UserDao userDao;
    private final TransactionManager tm ;

    public UserService(UserDao userDao, TransactionManager tm) {
        this.userDao = userDao;
        this.tm = tm;
    }

    public User save(User user) {
        try {
            tm.beginTransaction();
            User saveUser = userDao.save(user);
            tm.closeTransaction();
            return saveUser;
        } catch (Exception e) {
            log.error("Error saving user: {}", e.getMessage());
            throw new RuntimeException("Error saving user", e);
        }
    }

    public User findById(String accountId) {
        try {
            tm.beginTransaction();
            User user = userDao.findById(accountId).orElse(new User());
            tm.closeTransaction();
            return user;
        } catch (Exception e) {
            log.error("Error finding user by ID: {}", e.getMessage());
            System.err.println(e.getMessage());
            throw new RuntimeException("Error finding user by ID", e);
        }
    }

    public User update(User user) {
        try {
            tm.beginTransaction();
            User updateUser = userDao.update(user);
            tm.closeTransaction();
            return updateUser;
        } catch (Exception e) {
            log.error("Error updating user: {}", e.getMessage());
            throw new RuntimeException("Error updating user", e);
        }
    }

    public void delete(User user) {
        try {
            tm.beginTransaction();
            userDao.deleteById(user.getUserId());
            tm.closeTransaction();
        } catch (Exception e) {
            log.error("Error deleting user: {}", e.getMessage());
            throw new RuntimeException("Error deleting user", e);
        }
    }

    public List<User> findAll() {
        try {
            tm.beginTransaction();
            List<User> userList = userDao.findAll();
            tm.closeTransaction();
            return userList;
        } catch (Exception e) {
            log.error("Error finding all users: {}", e.getMessage());
            throw new RuntimeException("Error finding all users", e);
        }
    }
}