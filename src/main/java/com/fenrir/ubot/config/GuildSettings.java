package com.fenrir.ubot.config;

import com.fenrir.ubot.commands.CommandList;
import com.fenrir.ubot.commands.administration.Activation;
import com.fenrir.ubot.commands.administration.BotName;
import com.fenrir.ubot.commands.administration.Deactivation;
import net.dv8tion.jda.api.entities.Guild;

import java.awt.*;
import java.util.HashMap;
import java.util.Objects;

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

    public String getGuildID() {
        return guildID;
    }

    public String getGuildName() {
        return guildName;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public static void addGuildSettings(GuildSettings guildSettings) {
        settings.put(guildSettings.getGuildID(), guildSettings);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuildSettings that = (GuildSettings) o;
        return active == that.active
                && Objects.equals(guildID, that.guildID)
                && Objects.equals(guildName, that.guildName)
                && Objects.equals(prefix, that.prefix)
                && Objects.equals(botName, that.botName)
                && Objects.equals(welcomeChannelID, that.welcomeChannelID)
                && Objects.equals(errorChannelID, that.errorChannelID)
                && Objects.equals(modLogChannelID, that.modLogChannelID)
                && Objects.equals(messageColor, that.messageColor)
                && Objects.equals(infoColor, that.infoColor)
                && Objects.equals(helpColor, that.helpColor)
                && Objects.equals(warningColor, that.warningColor)
                && Objects.equals(errorColor, that.errorColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guildID, guildName, prefix, active, botName, welcomeChannelID, errorChannelID, modLogChannelID, messageColor, infoColor, helpColor, warningColor, errorColor);
    }
}
