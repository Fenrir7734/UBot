package com.fenrir.ubot.utilities.message;

public enum MessageCategory {

    MESSAGE("Message"),
    INFO("Info"),
    HELP("Help"),
    WARNING("Warning"),
    ERROR("Error"),
    NONE("");

    String value;

    MessageCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
