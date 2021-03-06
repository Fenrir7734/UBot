package com.fenrir.ubot.database;

import com.fenrir.ubot.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuildDB {

    private final static Logger logger = LoggerFactory.getLogger(GuildDB.class);

    private String guildID;

    private String prefix;
    private boolean active;
    private String botName;

    private String welcomeChannelID;
    private String logChannelID;

    private Color messageColor;
    private Color infoColor;
    private Color helpColor;
    private Color warningColor;
    private Color errorColor;

    private GuildDB(String id, Connection connection) {
        guildID = id;

        Config config = Config.getConfig();
        prefix = config.getPrefix();
        active = config.isActive();
        botName = config.getBotName();

        messageColor = config.getMessageColor();
        infoColor = config.getInfoColor();
        helpColor = config.getHelpColor();
        warningColor = config.getWarningColor();
        errorColor = config.getErrorColor();

        welcomeChannelID = null;
        logChannelID = null;
    }

    private GuildDB(ResultSet set) throws SQLException {
        guildID = set.getString("guild_id");

        prefix = set.getString("prefix");
        active = set.getBoolean("active");
        botName = set.getString("bot_name");

        welcomeChannelID = set.getString("welcome_channel_id");
        logChannelID = set.getString("log_channel_id");
    }

    public static GuildDB get(String id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM guild WHERE guild_id = ?");
        statement.setString(1, id);
        ResultSet set = statement.executeQuery();

        if(!set.next()) {
            return new GuildDB(id, connection);
        } else {
            return new GuildDB(set);
        }
    }

    public String getPrefix() {
        return prefix;
    }

    public String getBotName() {
        return botName;
    }

    public Color getErrorColor() {
        return errorColor;
    }

    public Color getWarningColor() {
        return warningColor;
    }

    public Color getMessageColor() {
        return messageColor;
    }

    public Color getHelpColor() {
        return helpColor;
    }

    public Color getInfoColor() {
        return infoColor;
    }

    public String getLogChannelID() {
        return logChannelID;
    }

    public String getWelcomeChannelID() {
        return welcomeChannelID;
    }

    public static boolean initializeTable(Connection connection) {
        try {
            logger.info("Attempting to create a table: guild.");

            connection.prepareStatement("CREATE TABLE IF NOT EXISTS guild (" +
                    "guild_id VARCHAR(100) PRIMARY  KEY," +
                    "prefix VARCHAR(3) NOT NULL," +
                    "bot_name VARCHAR(32) NOT NULL," +
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
