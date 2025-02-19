package com.socslingo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private static final String DB_NAME = "socslingo";
    private static final String MAINTENANCE_DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String TARGET_DB_URL = "jdbc:postgresql://localhost:5432/" + DB_NAME;

    public static void main(String[] args) {
        String username = "postgres";
        String password = "123456";

        try {
            try (Connection maintenanceConn = DriverManager.getConnection(MAINTENANCE_DB_URL, username, password)) {
                createDatabaseIfNotExists(maintenanceConn, DB_NAME);
            }

            try (Connection targetConn = DriverManager.getConnection(TARGET_DB_URL, username, password)) {
                System.out.println("Connected to " + DB_NAME + " database.");
                createTables(targetConn);
            }
        } catch (SQLException ex) {
            System.out.println("Error initializing the database.");
            ex.printStackTrace();
        }
    }

    private static void createDatabaseIfNotExists(Connection conn, String dbName) throws SQLException {
        String checkDatabaseExistsQuery = "SELECT 1 FROM pg_database WHERE datname = '" + dbName + "';";
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(checkDatabaseExistsQuery)) {
            if (!rs.next()) {
                stmt.execute("CREATE DATABASE " + dbName);
                System.out.println("Database " + dbName + " created successfully.");
            } else {
                System.out.println("Database " + dbName + " already exists.");
            }
        }
    }

    private static void createTables(Connection connection) throws SQLException {
        String createUserTable = "CREATE TABLE IF NOT EXISTS users ("
                + "id SERIAL PRIMARY KEY, "
                + "username VARCHAR(255) NOT NULL, "
                + "email VARCHAR(255) NOT NULL, "
                + "password VARCHAR(255) NOT NULL"
                + ");";

        String createFlashcardTable = "CREATE TABLE IF NOT EXISTS flashcard ("
                + "id SERIAL PRIMARY KEY, "
                + "front TEXT NOT NULL, "
                + "back TEXT NOT NULL"
                + ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createUserTable);
            statement.execute(createFlashcardTable);
            System.out.println("Tables created successfully.");

            String insertUser = "INSERT INTO users (username, email, password) "
                    + "VALUES ('demoUser', 'demo@example.com', 'password123');";
            statement.execute(insertUser);
            System.out.println("Sample user inserted successfully.");
            String[][] hiragana = {
                    { "あ", "a" }, { "い", "i" }, { "う", "u" }, { "え", "e" }, { "お", "o" },
                    { "か", "ka" }, { "き", "ki" }, { "く", "ku" }, { "け", "ke" }, { "こ", "ko" },
                    { "さ", "sa" }, { "し", "shi" }, { "す", "su" }, { "せ", "se" }, { "そ", "so" },
                    { "た", "ta" }, { "ち", "chi" }, { "つ", "tsu" }, { "て", "te" }, { "と", "to" },
                    { "な", "na" }, { "に", "ni" }, { "ぬ", "nu" }, { "ね", "ne" }, { "の", "no" },
                    { "は", "ha" }, { "ひ", "hi" }, { "ふ", "fu" }, { "へ", "he" }, { "ほ", "ho" },
                    { "ま", "ma" }, { "み", "mi" }, { "む", "mu" }, { "め", "me" }, { "も", "mo" },
                    { "や", "ya" }, { "ゆ", "yu" }, { "よ", "yo" },
                    { "ら", "ra" }, { "り", "ri" }, { "る", "ru" }, { "れ", "re" }, { "ろ", "ro" },
                    { "わ", "wa" }, { "を", "wo" }, { "ん", "n" }
            };

            for (String[] card : hiragana) {
                String front = card[0];
                String back = card[1];
                String insertHiragana = "INSERT INTO flashcard (front, back) VALUES ('" + front + "', '" + back + "');";
                statement.execute(insertHiragana);
            }
            System.out.println("All Hiragana flashcards inserted successfully.");
        }

    }
}
