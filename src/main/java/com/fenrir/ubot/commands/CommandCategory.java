package com.fenrir.ubot.commands;

public enum CommandCategory {

    OWNER("Owner"),
    SETTINGS("Settings"),
    FUN("Fun"),
    MEDIA("Media"),
    GENERAL("General");

    private final String value;

    CommandCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
