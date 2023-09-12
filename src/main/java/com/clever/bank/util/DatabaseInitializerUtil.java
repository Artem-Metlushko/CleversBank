package com.clever.bank.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseInitializerUtil {
    private DatabaseInitializerUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void runSqlScripts() {
        try(Connection connection = ConnectionManager.get();
            Statement statement = connection.createStatement();
            ) {
            statement.execute(readScript());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static String readScript() {
        Path scriptPath = Paths.get("src/main/resources/initialization.sql");
        try {
            byte[] scriptBytes = Files.readAllBytes(scriptPath);
            return new String(scriptBytes, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
