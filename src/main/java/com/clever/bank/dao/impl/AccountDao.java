package com.clever.bank.dao.impl;

import com.clever.bank.dao.Dao;
import com.clever.bank.mapper.MapperAccount;
import com.clever.bank.model.Account;
import com.clever.bank.util.TransactionManager;
import com.clever.bank.util.UuidWrapper;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@Slf4j
public class AccountDao implements Dao<String, Account> {
    private final UuidWrapper uuidWrapper ;
    private final TransactionManager tm ;

    public AccountDao(UuidWrapper uuidWrapper, TransactionManager tm) {
        this.uuidWrapper = uuidWrapper;
        this.tm = tm;
    }
    private static final String CREATE_ACCOUNT = """
            INSERT INTO "account" (account_id, opening_date, user_id, bank_id, balance)
            VALUES (?, ?, ?, ?, ?);
            """;

    private static final String FIND_ACCOUNT_BY_ID = "SELECT * FROM \"account\" WHERE account_id = ?";
    private static final String DELETE_ACCOUNT = "DELETE FROM \"account\" WHERE account_id = ?";
    private static final String FIND_ALL_ACCOUNTS = "SELECT * FROM \"account\"";

    private static final String GET_CURRENT_BALANCE = """
            SELECT balance, version
            FROM account
            WHERE account_id = ?
            """;

    private static final String UPDATE_BALANCE = """
            UPDATE account
            SET balance =  ?, version = ?
            WHERE account_id = ? and version = ?;
            """;

    private static final String UPDATE_ACCOUNT = """
            UPDATE "account"
            SET opening_date = ?, user_id = ?, bank_id = ?, balance = ? , version = ?
            WHERE account_id = ? and version = ?
            """;




    @Override
    public Account save(Account account) {
        account.setAccountId(uuidWrapper.randomUUID());
        var connection = tm.get();
        try (var ps = connection.prepareStatement(CREATE_ACCOUNT, RETURN_GENERATED_KEYS);
        ) {
            ps.setString(1, account.getAccountId());
            ps.setTimestamp(2, Timestamp.valueOf(account.getOpeningDate()));
            ps.setString(3, account.getUser().getUserId());
            ps.setString(4, account.getBank().getBankId());
            ps.setBigDecimal(5, account.getBalance());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                account.setAccountId(rs.getString(1));
            }

            log.info("Account saved: {}", account.getAccountId());
            return account;

        } catch (SQLException e) {
            throw new RuntimeException("Error saving account to database: " + e.getMessage(), e);
        }
    }

    @Override
    public Account update(Account account) {
        var connection = tm.get();
        try (var ps = connection.prepareStatement(UPDATE_ACCOUNT, RETURN_GENERATED_KEYS);
        ) {
            var updateVersion = account.getVersion() + 1;
            ps.setTimestamp(1, Timestamp.valueOf(account.getOpeningDate()));
            ps.setString(2, account.getUser().getUserId());
            ps.setString(3, account.getBank().getBankId());
            ps.setBigDecimal(4, account.getBalance());
            ps.setLong(5, updateVersion);
            ps.setString(6, account.getAccountId());
            ps.setLong(7, account.getVersion());
            ps.executeUpdate();

            log.info("Account updated: {}", account.getAccountId());
            return account;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating account in database: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Account> findById(String id) {
        var connection = tm.get();
        try (var ps = connection.prepareStatement(FIND_ACCOUNT_BY_ID);
        ) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account account = MapperAccount.getAccount(rs);
                log.info("Account found: {}", account.getAccountId());
                return Optional.of(account);
            } else {
                log.info("Account not found");
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding account by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteById(String id) {
        var connection = tm.get();
        try (var ps = connection.prepareStatement(DELETE_ACCOUNT);
        ) {
            ps.setString(1, id);
            int rowsAffected = ps.executeUpdate();
            boolean isDeleted = rowsAffected > 0;
            if (isDeleted) {
                log.info("Account deleted: {}", id);
            } else {
                log.info("Account not found for deletion: {}", id);
            }
            return isDeleted;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting account from database: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Account> findAll() {
        var connection = tm.get();
        try (var statement = connection.prepareStatement(FIND_ALL_ACCOUNTS);
        ) {
            ResultSet rs = statement.executeQuery();
            List<Account> accountList = new ArrayList<>();
            while (rs.next()) {
                Account account = MapperAccount.getAccount(rs);
                accountList.add(account);
            }
            log.info("Found {} accounts", accountList.size());
            return accountList;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all accounts: " + e.getMessage(), e);
        }
    }
}
