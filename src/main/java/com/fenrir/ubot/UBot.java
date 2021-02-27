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
import com.fenrir.ubot.listener.GuildCommandListener;
import com.fenrir.ubot.listener.GuildReadyListener;
import com.fenrir.ubot.listener.PrivateChannelListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.apache.logging.log4j.core.Logger;

import javax.security.auth.login.LoginException;

public class UBot {

    private static JDA client;
    private static CommandList commandList;
    private static Config config;
    private static Logger logger;

    public static void main(String[] args) {
        config = new Config();
        initCommands();
        bootBot();
    }

    public static void bootBot() {
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
        } catch (LoginException e) {
            System.out.println(e.getMessage()); //TODO: Zrobić coś z tym, najlepiej dodać logger
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void initCommands() {
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
    }

    public static JDA getClient() {
        return client;
    }
}
