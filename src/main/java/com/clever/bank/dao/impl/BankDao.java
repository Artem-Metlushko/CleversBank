package com.clever.bank.dao.impl;

import com.clever.bank.dao.Dao;
import com.clever.bank.mapper.MapperBank;
import com.clever.bank.model.Bank;
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
public class BankDao implements Dao<String, Bank> {
    private final UuidWrapper uuidWrapper ;
    private final TransactionManager tm ;

    public BankDao(UuidWrapper uuidWrapper, TransactionManager tm) {
        this.uuidWrapper = uuidWrapper;
        this.tm = tm;
    }
    private static final String CREATE_BANK = """
            INSERT INTO "bank" (bank_id, name)
            VALUES (?, ?);
            """;
    private static final String UPDATE_BANK = """
            UPDATE "bank" 
            SET name = ? 
            WHERE bank_id = ?
            """;
    private static final String FIND_BANK_BY_ID = "SELECT * FROM \"bank\" WHERE bank_id = ?";
    private static final String DELETE_BANK = "DELETE FROM \"bank\" WHERE bank_id = ?";
    private static final String FIND_ALL_BANKS = "SELECT * FROM \"bank\"";




    @Override
    public Bank save(Bank bank) {
        bank.setBankId(uuidWrapper.randomUUID());
        var connection = tm.get();
        try (var ps = connection.prepareStatement(CREATE_BANK, RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, bank.getBankId());
            ps.setString(2, bank.getName());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                bank.setBankId(rs.getString(1));
            }

            log.info("Bank saved: {}", bank.getBankId());
            return bank;

        } catch (SQLException e) {
            log.error("Error saving bank to database: {}", e.getMessage());
            throw new RuntimeException("Error saving bank to database", e);
        }
    }

    @Override
    public Bank update(Bank bank) {
        var connection = tm.get();
        try (var ps = connection.prepareStatement(UPDATE_BANK, RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, bank.getName());
            ps.setString(2, bank.getBankId());
            ps.executeUpdate();

            log.info("Bank updated: {}", bank.getBankId());
            return bank;

        } catch (SQLException e) {
            log.error("Error updating bank in database: {}", e.getMessage());
            throw new RuntimeException("Error updating bank in database", e);
        }
    }

    @Override
    public Optional<Bank> findById(String id) {
        var connection = tm.get();
        try (var ps = connection.prepareStatement(FIND_BANK_BY_ID)
        ) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            Bank bank = new Bank();
            if (rs.next()) {
                bank = MapperBank.getBank(rs);
                log.info("Bank found: {}", bank.getBankId());
            } else {
                log.info("Bank not found");
            }
            return Optional.of(bank);
        } catch (SQLException e) {
            log.error("Error fetching bank from database: {}", e.getMessage());
            throw new RuntimeException("Error fetching bank from database", e);
        }
    }

    @Override
    public boolean deleteById(String id) {
        var connection = tm.get();
        try (var ps = connection.prepareStatement(DELETE_BANK)
        ) {
            ps.setString(1, id);
            int rowsAffected = ps.executeUpdate();
            boolean isDeleted = rowsAffected > 0;
            if (isDeleted) {
                log.info("Bank deleted: {}", id);
            } else {
                log.info("Bank not found for deletion: {}", id);
            }
            return isDeleted;
        } catch (SQLException e) {
            log.error("Error deleting bank from database: {}", e.getMessage());
            throw new RuntimeException("Error deleting bank from database", e);
        }
    }

    @Override
    public List<Bank> findAll() {
        var connection = tm.get();
        try (var statement = connection.createStatement()
        ) {
            ResultSet rs = statement.executeQuery(FIND_ALL_BANKS);
            List<Bank> bankList = new ArrayList<>();
            while (rs.next()) {
                Bank bank = MapperBank.getBank(rs);
                bankList.add(bank);
            }
            log.info("Found {} banks", bankList.size());
            return bankList;
        } catch (SQLException e) {
            log.error("Error finding all banks: {}", e.getMessage());
            throw new RuntimeException("Error finding all banks", e);
        }
    }
}
