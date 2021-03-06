/*
  TODO: Nazwa bota w Config może być niezgona z jest Nickname na serwerze
  TODO: Dodać Logger
*/

package com.fenrir.ubot;

import com.fenrir.ubot.commands.CommandList;
import com.fenrir.ubot.commands.general.Meme;
import com.fenrir.ubot.commands.general.Roll;
import com.fenrir.ubot.commands.general.Help;
import com.fenrir.ubot.commands.general.Ping;
import com.fenrir.ubot.commands.administration.Activation;
import com.fenrir.ubot.commands.administration.BotName;
import com.fenrir.ubot.commands.administration.Deactivation;
import com.fenrir.ubot.commands.moderation.Purge;
import com.fenrir.ubot.config.Config;
import com.fenrir.ubot.database.Database;
import com.fenrir.ubot.listener.GuildCommandListener;
import com.fenrir.ubot.listener.GuildReadyListener;
import com.fenrir.ubot.listener.PrivateChannelListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.sql.SQLException;

public class UBot {

    private static final Logger log = LoggerFactory.getLogger(UBot.class);

    private static JDA client;
    private static CommandList commandList;
    private static Database database;
    private static Config config;

    public static void main(String[] args) {
        log.info("Starting...");

        try {
            log.info("Loading configuration...");

            config = new Config();

            log.info("Configuration loaded.");

            initCommands();
            bootBot();

            log.info("Starting database initialization process...");
            database = new Database();
            log.info("Database initialized successfully.");

        } catch (IOException e) {
            log.error("Failed to read the {} file.", "Config.json");
            System.exit(0);
        } catch (JSONException e) {
            log.error("The configuration file is invalid.");
            System.exit(0);
        } catch (SQLException e) {
            log.error("Stopping the bot");
            System.exit(0);
        } catch (Exception e) {
            log.error("Something went wrong: {}", e.getMessage());
            log.error("Stopping the bot");
            System.exit(0);
        }

        log.info("The bot has been successfully built and is ready to go!");
    }

    public static void bootBot() {
        log.info("Try to build new JDA instance...");
        try {
            client = JDABuilder.createDefault(config.getToken())
                    .addEventListeners(
                            new GuildCommandListener(),
                            new GuildReadyListener(),
                            new PrivateChannelListener()
                    )
                    .build();
            client.awaitReady();
            Config.getConfig().setBotName(client.getSelfUser().getName());
        } catch (LoginException | IllegalArgumentException e) {
            log.error("New JDA instance could not be created. Check if provided token is valid.");
            log.error("Stopping the bot");
            System.exit(0);
        } catch (InterruptedException e) {
            log.error("Thread was interrupted during creating new JDA instance.");
            log.error("Stopping the bot");
            System.exit(0);
        }
        log.info("JDA instance build successfully.");
    }

    private static void initCommands() {
        log.info("Initiating commands...");

        CommandList.getCommandList().addInitCommands(
                new Ping(),
                new Activation(),
                new Deactivation(),
                new BotName()
        );

        CommandList.getCommandList().addCommands(
                new Help(),
                new Roll(),
                new Purge(),
                new Meme()
        );
        log.info("Commands initialized successfully.");
    }

    public static JDA getClient() {
        return client;
    }
}
