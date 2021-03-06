package com.fenrir.ubot.database;

import com.fenrir.ubot.config.Config;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import net.dv8tion.jda.api.entities.Guild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class GuildDB {

    private final static Logger logger = LoggerFactory.getLogger(GuildDB.class);

    private String guildID;

    private String prefix;
    private boolean active;
    private String botName;

    private String welcomeChannelID;
    private String modLogChannelID;

    private Color messageColor;
    private Color infoColor;
    private Color helpColor;
    private Color warningColor;
    private Color errorColor;

    public GuildDB(String id, Connection connection) {
        this.guildID = id;

        Config config = Config.getConfig();
        this.prefix = config.getPrefix();
        this.active = config.isActive();
        this.botName = config.getBotName();

        messageColor = config.getMessageColor();
        infoColor = config.getInfoColor();
        helpColor = config.getHelpColor();
        warningColor = config.getWarningColor();
        errorColor = config.getErrorColor();

        welcomeChannelID = null;
        modLogChannelID = null;
    }

    public static boolean initializeTable(Connection connection) {
        try {
            logger.info("Attempting to create a table: guild.");

            connection.prepareStatement("CREATE TABLE IF NOT EXISTS guild (" +
                    "guild_id VARCHAR(100) PRIMARY  KEY," +
                    "prefix VARCHAR(3) NOT NULL," +
                    "active BIT NOT NULL," +
                    "welcome_channel_id VARCHAR(100) NULL," +
                    "log_channel_id VARCHAR(100) NULL," +
                    "message_color CHAR(6) NOT NULL," +
                    "info_color CHAR(6) NOT NULL," +
                    "help_color CHAR(6) NOT NULL," +
                    "warning_color CHAR(6) NOT NULL," +
                    "error_color CHAR(6) NOT NULL" +
                    ");"
            ).execute();

            logger.info("Successfully created table: guild.");
        } catch (SQLException e) {
            logger.error("Table guild could not be created: {}.", e.getMessage());
            return false;
        }
        return true;
    }
}
