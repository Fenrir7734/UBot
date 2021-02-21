package com.fenrir.ubot.config;

import net.dv8tion.jda.api.entities.Guild;

import java.awt.*;
import java.util.HashMap;

public class GuildSettings {

    private static HashMap<String, GuildSettings> settings = new HashMap<>();

    private final String guildID;
    private String guildName;

    private String prefix;
    private boolean active;
    private String botName;

    private String welcomeChannelID;
    private String errorChannelID;
    private String modLogChannelID;

    private Color messageColor;
    private Color infoColor;
    private Color helpColor;
    private Color warningColor;
    private Color errorColor;

    public GuildSettings(Guild guild) {

        guildID = guild.getId();
        guildName = guild.getName();

        prefix = Config.getConfig().getPrefix();
        botName = Config.getConfig().getBotName();
        active = false;

        errorChannelID = null;
        welcomeChannelID = null;
        modLogChannelID = null;

        messageColor = Color.CYAN;
        infoColor = Color.BLUE;
        warningColor = Color.YELLOW;
        errorColor = Color.RED;
        helpColor = Color.GREEN;

        settings.putIfAbsent(guildID, this);
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

    public String getWelcomeChannelID() {
        return welcomeChannelID;
    }

    public String getErrorChannelID() {
        return errorChannelID;
    }

    public String getModLogChannelID() {
        return modLogChannelID;
    }

    public Color getMessageColor() {
        return messageColor;
    }

    public Color getInfoColor() {
        return infoColor;
    }

    public Color getHelpColor() {
        return helpColor;
    }

    public Color getWarningColor() {
        return warningColor;
    }

    public Color getErrorColor() {
        return errorColor;
    }

    public static GuildSettings getSettings(Guild guild) {
        return settings.getOrDefault(guild.getId(), null);
    }
}
