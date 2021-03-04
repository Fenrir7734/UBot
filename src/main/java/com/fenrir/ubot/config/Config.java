package com.fenrir.ubot.config;

import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Config {

    private static Config config = null;

    private final String path;

    private String databaseName;
    private String databaseLogin;
    private String databasePassword;

    private String token;
    private String prefix;

    private String ownerID;

    private boolean active;
    private String botName;

    private Color messageColor;
    private Color infoColor;
    private Color warningColor;
    private Color errorColor;
    private Color helpColor;

    public Config() throws IOException, JSONException {
        path = "Config.json";
        config = this;
        active = true;
        botName = "U-BOT";

        messageColor = Color.CYAN;
        infoColor = Color.BLUE;
        warningColor = Color.YELLOW;
        errorColor = Color.RED;
        helpColor = Color.GREEN;

        load();
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public static Config getConfig() {
        return config;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabaseLogin() {
        return databaseLogin;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public String getToken() {
        return token;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getBotName() {
        return botName;
    }

    public Color getMessageColor() {
        return messageColor;
    }

    public Color getInfoColor() {
        return infoColor;
    }

    public Color getWarningColor() {
        return warningColor;
    }

    public Color getErrorColor() {
        return errorColor;
    }

    public Color getHelpColor() {
        return helpColor;
    }

    public boolean isActive() {
        return active;
    }

    private void load() throws JSONException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(path)));
        JSONObject object = new JSONObject(content);

        token = object.getString("token");
        prefix = object.getString("prefix");

        databaseName = object.getString("database-name");
        databaseLogin = object.getString("database-login");
        databasePassword = object.getString("database-password");
    }

}
