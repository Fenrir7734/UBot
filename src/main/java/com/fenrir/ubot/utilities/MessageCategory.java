package com.fenrir.ubot.utilities;

public enum MessageCategory {

    MESSAGE("Message"),
    INFO("Info"),
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
