package com.fenrir.ubot.database;

import com.fenrir.ubot.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLManager {
    private final Logger logger = LoggerFactory.getLogger(SQLManager.class);
    private final Config config = Config.getConfig();

    private Connection connection;

    public SQLManager() {
        logger.info("Attempting to connect to the database.");
        try {
            String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/" +
                    config.getDatabaseName() +
                    "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            connection = DriverManager.getConnection(jdbcUrl, config.getDatabaseLogin(), config.getDatabasePassword());

            logger.info("Connection to database established.");
        } catch (SQLException e) {
            logger.error("Connection to the database could not be established.");
        }
    }
}
