package com.fenrir.ubot.database;

import com.fenrir.ubot.config.Config;
import com.fenrir.ubot.utilities.Utilities;
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

        addGuildToDatabase(this, connection);
    }

    private GuildDB(ResultSet set) throws SQLException {
        guildID = set.getString("guild_id");

        prefix = set.getString("prefix");
        active = set.getBoolean("active");
        botName = set.getString("bot_name");

        messageColor = Color.decode("#" + set.getString("message_color"));
        infoColor = Color.decode("#" + set.getString("info_color"));
        helpColor = Color.decode("#" + set.getString("help_color"));
        warningColor = Color.decode("#" + set.getString("warning_color"));
        errorColor = Color.decode("#" + set.getString("error_color"));

        welcomeChannelID = set.getString("welcome_channel_id");
        logChannelID = set.getString("log_channel_id");
    }

    public static GuildDB get(String id, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM guild WHERE guild_id = ?")) {

            statement.setString(1, id);
            ResultSet set = statement.executeQuery();

            if(!set.next()) {
                return new GuildDB(id, connection);
            } else {
                return new GuildDB(set);
            }
        } catch (SQLException e) {
            logger.error("An error occurred while fetching guild from database. Guild id: {}", id);
            return null;
        }
    }

    private boolean addGuildToDatabase(GuildDB guildDB, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement("" +
                "INSERT INTO guild (guild_id, prefix, bot_name, active, welcome_channel_id, log_channel_id, message_color, info_color, help_color, warning_color, error_color) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?)")) {


            statement.setString(1, guildDB.getGuildID());
            statement.setString(2, guildDB.getPrefix());
            statement.setString(3, guildDB.getBotName());
            statement.setBoolean(4, guildDB.isActive());
            statement.setString(5, guildDB.getWelcomeChannelID());
            statement.setString(6, guildDB.getLogChannelID());
            statement.setString(7, Utilities.colorToHex(guildDB.getMessageColor()));
            statement.setString(8, Utilities.colorToHex(guildDB.getInfoColor()));
            statement.setString(9, Utilities.colorToHex(guildDB.getHelpColor()));
            statement.setString(10, Utilities.colorToHex(guildDB.getWarningColor()));
            statement.setString(11, Utilities.colorToHex(guildDB.getErrorColor()));

            return statement.execute();
        } catch (SQLException e) {
            logger.error("An error occurred while inserting guild to database. {}", e.getMessage());
        }

        return false;
    }

    public static GuildDB add(String id, Connection connection) {
        return new GuildDB(id, connection);
    }

    public void updateValue(String column, String value, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE guild " +
                "SET ? = ?" +
                "WHERE guild_id = ?")) {

            statement.setString(1, column);
            statement.setString(2, value);
            statement.setString(3, this.guildID);
            statement.execute();
        } catch (SQLException e) {
            logger.error("An error occurred while updating guild table: column: {}, value: {}", column, value);
        }
    }

    public void updateValue(String column, Boolean value, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE guild " +
                "SET ? = ?" +
                "WHERE guild_id = ?")) {

            statement.setString(1, column);
            statement.setBoolean(2, value);
            statement.setString(3, this.guildID);
            statement.execute();
        } catch (SQLException e) {
            logger.error("An error occurred while updating guild table: column: {}, value: {}", column, value);
        }
    }

    public String getGuildID() {
        return guildID;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isActive() {
        return active;
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
