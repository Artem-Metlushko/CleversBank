/*
package com.clever.bank.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.Connection.TRANSACTION_REPEATABLE_READ;
@Slf4j
public class TransactionManager {
    private Connection connection;

    public void beginTransaction() {
        try {
            System.out.println(" Begin transaction");
            connection = ConnectionManager.get();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(TRANSACTION_REPEATABLE_READ);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Connection get() {
        return connection;
    }

    public boolean closeTransaction()  {
        try {

            System.out.println( "Commit transaction");
            connection.commit();
        } catch (Exception e) {
            if (this.get() != null) {
                System.out.println( "Rollback transaction");
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                return false;
            }
        } finally {
            if (this.get() != null) {
                System.out.println("Close connection");
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return true;
    }
}



*/

package com.clever.bank.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.Connection.TRANSACTION_REPEATABLE_READ;

@Slf4j
public class TransactionManager {
    private Connection connection;

    public void beginTransaction() {
        try {
            log.debug("Begin transaction");
            connection = ConnectionManager.get();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(TRANSACTION_REPEATABLE_READ);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Connection get() {
        return connection;
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public boolean closeTransaction() {
        try {
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return true;
    }
}
