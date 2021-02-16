package com.fenrir.ubot.utilities;

import com.fenrir.ubot.config.Config;

public enum CommandErrorsMsg {

    ONLY_OWNER("Only the owner can execute this command"),
    INACTIVE("Bot is deactivated"),

    TOO_FEW_ARGUMENTS("Too few arguments!"),
    TOO_MUCH_ARGUMENTS("Too much arguments!"),
    INVALID_ARGUMENTS("Invalid argument!"),
    INVALID_FLAG("Invalid flag"),

    USER_TOO_LOW_PERMISSION("You do not have the required permissions"),

    BOT_TOO_LOW_PERMISSION(Config.getConfig().getBotName() + " does not have the required permissions");

    String value;

    CommandErrorsMsg(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
