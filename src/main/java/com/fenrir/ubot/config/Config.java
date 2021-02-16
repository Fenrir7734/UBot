package com.fenrir.ubot.config;

import org.json.JSONObject;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Config {

    private static Config config = null;

    private final String path;

    private String token;
    private String prefix;

    private boolean active;
    private String botName;

    private Color messageColor;
    private Color infoColor;
    private Color warningColor;
    private Color errorColor;
    private Color helpColor;

    public Config() {
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

    private void load() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(path)));
            JSONObject object = new JSONObject(content);

            token = object.getString("token");
            prefix = object.getString("prefix");

        } catch (Exception e) {
            System.out.println(e.getMessage()); //TODO: Coś z tym zrobić i dodać Logger
        }
    }

}
