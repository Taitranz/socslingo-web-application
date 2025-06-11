package com.socslingo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Database initializer for SQLite database.
 * Creates tables and populates them with initial data for the Socslingo application.
 */
public class SqliteDatabaseInitializer {
    
    private static final Logger logger = Logger.getLogger(SqliteDatabaseInitializer.class.getName());
    private static final String DATABASE_NAME = "socslingo.db";
    private static final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_NAME;

    public static void main(String[] args) {
        try {
            initializeDatabase();
            logger.info("SQLite database initialization completed successfully.");
        } catch (SQLException exception) {
            logger.log(Level.SEVERE, "Error initializing SQLite database", exception);
            System.exit(1);
        }
    }

    /**
     * Initializes the SQLite database by creating tables and inserting sample data.
     */
    public static void initializeDatabase() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
            logger.info("Connected to SQLite database: " + DATABASE_NAME);
            
            createTables(connection);
            insertSampleData(connection);
            
            logger.info("Database initialization completed successfully.");
        }
    }

    /**
     * Creates the necessary tables for the application.
     */
    private static void createTables(Connection connection) throws SQLException {
        String createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username VARCHAR(255) NOT NULL UNIQUE,
                email VARCHAR(255) NOT NULL UNIQUE,
                password VARCHAR(255) NOT NULL,
                created_at DATETIME DEFAULT CURRENT_TIMESTAMP
            );
            """;

        String createFlashcardTable = """
            CREATE TABLE IF NOT EXISTS flashcard (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                front TEXT NOT NULL,
                back TEXT NOT NULL,
                created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                user_id INTEGER,
                FOREIGN KEY (user_id) REFERENCES users(id)
            );
            """;

        String createFlashcardSetTable = """
            CREATE TABLE IF NOT EXISTS flashcard_set (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name VARCHAR(255) NOT NULL,
                description TEXT,
                user_id INTEGER NOT NULL,
                created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (user_id) REFERENCES users(id)
            );
            """;

        String createFlashcardSetMembershipTable = """
            CREATE TABLE IF NOT EXISTS flashcard_set_membership (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                flashcard_id INTEGER NOT NULL,
                set_id INTEGER NOT NULL,
                FOREIGN KEY (flashcard_id) REFERENCES flashcard(id),
                FOREIGN KEY (set_id) REFERENCES flashcard_set(id),
                UNIQUE(flashcard_id, set_id)
            );
            """;

        try (Statement statement = connection.createStatement()) {
            statement.execute(createUsersTable);
            logger.info("Users table created successfully.");
            
            statement.execute(createFlashcardTable);
            logger.info("Flashcard table created successfully.");
            
            statement.execute(createFlashcardSetTable);
            logger.info("Flashcard set table created successfully.");
            
            statement.execute(createFlashcardSetMembershipTable);
            logger.info("Flashcard set membership table created successfully.");
        }
    }

    /**
     * Inserts sample data into the database for testing and demonstration purposes.
     */
    private static void insertSampleData(Connection connection) throws SQLException {
        insertSampleUsers(connection);
        insertHiraganaFlashcards(connection);
    }

    /**
     * Inserts sample users into the database.
     */
    private static void insertSampleUsers(Connection connection) throws SQLException {
        String insertSampleUser = """
            INSERT OR IGNORE INTO users (username, email, password) 
            VALUES ('demoUser', 'demo@example.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqL8JGAb1PYdsHpxJKKtQBm');
            """;

        try (Statement statement = connection.createStatement()) {
            statement.execute(insertSampleUser);
            logger.info("Sample user inserted successfully.");
        }
    }

    /**
     * Inserts Hiragana flashcards for Japanese language learning.
     */
    private static void insertHiraganaFlashcards(Connection connection) throws SQLException {
        String[][] hiraganaCharacters = {
            // Basic Hiragana characters with romanization
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

        try (Statement statement = connection.createStatement()) {
            for (String[] character : hiraganaCharacters) {
                String frontSide = character[0];
                String backSide = character[1];
                String insertHiraganaQuery = String.format(
                    "INSERT OR IGNORE INTO flashcard (front, back) VALUES ('%s', '%s');",
                    frontSide.replace("'", "''"), // Escape single quotes for SQL safety
                    backSide.replace("'", "''")
                );
                statement.execute(insertHiraganaQuery);
            }
            logger.info("All Hiragana flashcards inserted successfully.");
        }
    }
}
