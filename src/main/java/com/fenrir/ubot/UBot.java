/**
 * TODO: Nazwa bota w Config może być niezgona z jest Nickname na serwerze
 *
 */

package com.fenrir.ubot;

import com.fenrir.ubot.commands.CommandList;
import com.fenrir.ubot.commands.general.Dice;
import com.fenrir.ubot.commands.general.Ping;
import com.fenrir.ubot.commands.owner.Activation;
import com.fenrir.ubot.commands.owner.BotName;
import com.fenrir.ubot.commands.owner.Deactivation;
import com.fenrir.ubot.config.Config;
import com.fenrir.ubot.listener.CommandListener;
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
                    .addEventListeners(new CommandListener())
                    .build();
            client.awaitReady();
            Config.getConfig().setBotName(client.getSelfUser().getName());
        } catch (LoginException e) {

        } catch (InterruptedException e) {

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
                new Dice()
        );
    }

    public static JDA getClient() {
        return client;
    }
}
