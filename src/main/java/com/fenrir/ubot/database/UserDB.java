package com.fenrir.ubot.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDB {
    private final static Logger logger = LoggerFactory.getLogger(UserDB.class);

    public static boolean initializeTable(Connection connection) {
        try {
            logger.info("Attempting to create a table: guild_user.");

            connection.prepareStatement("CREATE TABLE IF NOT EXISTS guild_user (" +
                    "user_id VARCHAR(100)," +
                    "guild_id VARCHAR(100) NOT NULL," +
                    "PRIMARY KEY(user_id, guild_id)," +
                    "FOREIGN KEY (guild_id) REFERENCES guild(guild_id)" +
                    ");"
            ).execute();
            logger.info("Successfully created table: guild_user.");
        } catch (SQLException e) {
            logger.error("Table guild_user could not be created: {}.", e.getMessage());
            return false;
        }
        return true;
    }
}
