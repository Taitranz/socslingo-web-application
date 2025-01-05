package com.socslingo.database;

import java.io.File;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    private static final String DATABASE_URL = "jdbc:sqlite:src/main/resources/database.db";

    public static void main(String[] args) {
        File databaseFile = new File("src/main/resources/database.db");
        if (databaseFile.exists()) {
            System.out.println("Database already exists");
            return;
        }

        try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
            if (connection != null) {
                System.out.println("Connected to SQLite database.");
                createTables(connection);
            }
        } catch (SQLException error) {
            System.out.println("Error connecting to SQLite database.");
            error.printStackTrace();
        }
    }

    private static void createTables(Connection connection) throws SQLException {
        String createUserTable = "CREATE TABLE IF NOT EXISTS users (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "username TEXT NOT NULL,\n"
                + "email TEXT NOT NULL,\n"
                + "password TEXT NOT NULL\n"
                + ");";
        try (Statement statement = connection.createStatement()) {
            statement.execute(createUserTable);

            String createFlashcardTable = "CREATE TABLE IF NOT EXISTS flashcard (\n"
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "front TEXT NOT NULL,\n"
                    + "back TEXT NOT NULL\n"
                    + ");";
            statement.execute(createFlashcardTable);
            System.out.println("Users table created successfully.");
        }
    }
}
