package com.fenrir.ubot.util;

public enum BasicMessages {

    ONLY_OWNER("Only the owner can execute this command"),
    INACTIVE("bot is deactivated "),

    TOO_FEW_ARGUMENTS("Too few arguments!"),
    TOO_MUCH_ARGUMENTS("Too much arguments!");

    String value;

    BasicMessages(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
