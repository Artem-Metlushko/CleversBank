package com.clever.bank.dao.impl;

import com.clever.bank.dao.Dao;
import com.clever.bank.mapper.MapperTransaction;
import com.clever.bank.model.Transaction;
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
public class TransactionDao implements Dao<String, Transaction> {
    private final UuidWrapper uuidWrapper ;
    private final TransactionManager tm ;
    public TransactionDao(UuidWrapper uuidWrapper, TransactionManager tm) {
        this.uuidWrapper = uuidWrapper;
        this.tm = tm;
    }
    private static final String CREATE_TRANSACTION = """
            INSERT INTO transaction (transaction_id, timestamp, type_transaction, from_account_id, to_account_id, amount)
            VALUES (?, ?, ?, ?, ?, ?);
            """;
    private static final String FIND_BY_ID = "SELECT * FROM transaction WHERE transaction_id = ?";
    private static final String DELETE_TRANSACTION = "DELETE FROM transaction WHERE transaction_id = ?";
    private static final String FIND_ALL = "SELECT * FROM transaction";



    @Override
    public Transaction save(Transaction transaction) {
        transaction.setTransactionId(uuidWrapper.randomUUID());
        var connection = tm.get();
        try (var ps = connection.prepareStatement(CREATE_TRANSACTION, RETURN_GENERATED_KEYS)) {
            ps.setString(1, transaction.getTransactionId());
            ps.setTimestamp(2, Timestamp.valueOf(transaction.getTimestamp()));
            ps.setString(3, transaction.getTypeTransaction().toString());
            ps.setString(4, transaction.getFromAccount().getAccountId());
            ps.setString(5, transaction.getToAccount().getAccountId());
            ps.setBigDecimal(6, transaction.getAmount());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                transaction.setTransactionId(rs.getString(1));
            }

            log.info("Transaction saved: {}", transaction.getTransactionId());
            return transaction;
        } catch (SQLException e) {
            log.error("Error saving transaction to database: {}", e.getMessage());
            throw new RuntimeException("Error saving transaction to database", e);
        }
    }

    @Override
    public Transaction update(Transaction entity) {
        log.info("Updating transaction is not supported");
        return entity;
    }

    @Override
    public Optional<Transaction> findById(String id) {
        var connection = tm.get();
        try (var ps = connection.prepareStatement(FIND_BY_ID)
        ) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Transaction transaction = MapperTransaction.getTransaction(rs);
                log.info("Transaction found: {}", id);
                return Optional.of(transaction);
            }
            log.info("Transaction not found: {}", id);
            return Optional.empty();
        } catch (SQLException e) {
            log.error("Error fetching transaction from database: {}", e.getMessage());
            throw new RuntimeException("Error fetching transaction from database", e);
        }
    }

    @Override
    public boolean deleteById(String id) {
        var connection = tm.get();
        try (var ps = connection.prepareStatement(DELETE_TRANSACTION)) {
            ps.setString(1, id);
            int rowsAffected = ps.executeUpdate();
            boolean isDeleted = rowsAffected > 0;
            if (isDeleted) {
                log.info("Transaction deleted: {}", id);
            } else {
                log.info("Transaction not found for deletion: {}", id);
            }
            return isDeleted;
        } catch (SQLException e) {
            log.error("Error deleting transaction from database: {}", e.getMessage());
            throw new RuntimeException("Error deleting transaction from database", e);
        }
    }

    @Override
    public List<Transaction> findAll() {
        var connection = tm.get();
        try (var statement = connection.createStatement()
        ) {
            ResultSet rs = statement.executeQuery(FIND_ALL);
            List<Transaction> transactionList = new ArrayList<>();
            while (rs.next()) {
                Transaction transaction = MapperTransaction.getTransaction(rs);
                transactionList.add(transaction);
            }
            log.info("Found {} transactions", transactionList.size());
            return transactionList;
        } catch (SQLException e) {
            log.error("Error finding all transactions: {}", e.getMessage());
            throw new RuntimeException("Error finding all transactions", e);
        }
    }
}
