package com.fenrir.ubot.commands.general;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandCategory;
import com.fenrir.ubot.commands.CommandEvent;
import com.fenrir.ubot.utilities.CommandVerifier;
import com.fenrir.ubot.utilities.PermissionsVerifier;
import net.dv8tion.jda.api.Permission;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class Ping extends Command {

    public Ping() {
        super();
        category = CommandCategory.GENERAL;
        isOnlyGuild = false;
    }

    @Override
    public void execute(CommandEvent event) {

        if (!CommandVerifier.isCommandCorrect(event, minNumberOfArguments, maxNumberOfArguments, flags)
                || !PermissionsVerifier.checkPermissions(botRequiredPermissions, event)
                || isHelpFlag(event)) {
            return;
        }

        long currentTime = System.currentTimeMillis();

        event.getChannel().sendMessage("Pong")
                .queue(time ->
                        time.editMessage("Pong " + (System.currentTimeMillis() - currentTime) + "ms")
                                .queue(newMessage -> newMessage.delete().queueAfter(2, TimeUnit.MINUTES)));
    }

    @Override
    public String getCommand() {
        return "ping";
    }

    @Override
    public String getBriefDescription() {
        return "Checks if the bot is available";
    }

    @Override
    public String getSpecificDescription() {
        return "Checks if the bot is available by replying with a message containing the word \"Pong\" " +
                "and the time it takes for the bot to reply and send the message. " +
                "Command takes no arguments.\n" +
                "*SYNOPSIS*:\n" +
                "`<prefix>ping [FLAG]`\n" +
                "*FLAGS*:\n" +
                "`-h` displays detailed help.";
    }
}
