package com.fenrir.ubot.database;

import com.fenrir.ubot.config.Config;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class Database {
    private final Logger logger = LoggerFactory.getLogger(Database.class);
    private final Config config = Config.getConfig();

    private Connection connection;

    private Cache<String,GuildDB> guilds;
    private Cache<String,UserDB> users;

    public Database() throws SQLException {
        logger.info("Attempting to connect to the database.");
        if(!connect()) {
            throw new SQLException();
        }
        logger.info("Connection to database established.");

        logger.info("Starting creating tables...");
        if(!GuildDB.initializeTable(connection) || !UserDB.initializeTable(connection)) {
            logger.error("Creation of tables has been aborted.");
            throw new SQLException();
        }
        logger.info("All tables created successfully.");


        guilds = Caffeine.newBuilder()
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();
        users = Caffeine.newBuilder()
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();

    }

    public GuildDB guildDB() {
        return null;
    }

    public UserDB getUser() {
        return null;
    }

    private boolean connect() {
        try {
            String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/" +
                    config.getDatabaseName() +
                    "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            connection = DriverManager.getConnection(jdbcUrl, config.getDatabaseLogin(), config.getDatabasePassword());
        } catch (SQLException e) {
            logger.error("Connection to the database could not be established.");
            return false;
        }
        return true;
    }

}
