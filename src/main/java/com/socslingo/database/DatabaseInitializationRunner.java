package com.socslingo.database;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Spring Boot component that initializes the SQLite database on application startup.
 * This ensures the database is properly set up before the application starts serving requests.
 */
@Component
public class DatabaseInitializationRunner implements CommandLineRunner {
    
    private static final Logger logger = Logger.getLogger(DatabaseInitializationRunner.class.getName());

    @Override
    public void run(String... args) throws Exception {
        try {
            logger.info("Starting database initialization...");
            SqliteDatabaseInitializer.initializeDatabase();
            logger.info("Database initialization completed successfully.");
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "Failed to initialize database", exception);
            // Don't throw the exception to prevent application from failing to start
            // The JPA DDL will handle table creation if this fails
        }
    }
}
