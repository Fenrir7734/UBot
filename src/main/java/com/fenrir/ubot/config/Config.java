package com.fenrir.ubot.config;


import com.fenrir.ubot.UBot;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;


public class Config {

    private static Config config = null;

    private final String path;

    private String token;
    private String prefix;

    private boolean active;
    private String botName;

    public Config() {
        path = "Config.json";
        config = this;
        active = false;
        botName = "U-BOT";
        load();
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    private void load() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(path)));
            JSONObject object = new JSONObject(content);

            token = object.getString("token");
            prefix = object.getString("prefix");

        } catch (Exception e) {

        }
    }

}
