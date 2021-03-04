package com.fenrir.ubot.commands.administration;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandCategory;
import com.fenrir.ubot.commands.CommandEvent;
import com.fenrir.ubot.config.Config;
import com.fenrir.ubot.utilities.*;
import com.fenrir.ubot.utilities.message.CommandErrorsMsg;
import com.fenrir.ubot.utilities.message.MessageCategory;
import com.fenrir.ubot.utilities.message.Messages;

public class Activation extends Command {

    public Activation() {
        super();
        category = CommandCategory.OWNER;
    }

    @Override
    public void execute(CommandEvent event) {

        if (!CommandVerifier.isCommandCorrect(event, minNumberOfArguments, maxNumberOfArguments, flags)
                || !PermissionsVerifier.checkPermissions(botRequiredPermissions, event)
                || isHelpFlag(event)) {
            return;
        }

        if (!event.isOwner()) {
            Messages.sendEmbedMessage(CommandErrorsMsg.ONLY_OWNER, MessageCategory.INFO, event.getChannel(), 60);
            return;
        }

        if (Config.getConfig().isActive()) {
            Messages.sendEmbedMessage(Config.getConfig().getBotName() + " has already been activated!", MessageCategory.WARNING, event.getChannel(), 60);
            return;
        }

        Config.getConfig().setActive(true);
        Messages.sendEmbedMessage(Config.getConfig().getBotName() + " has been activated!", MessageCategory.INFO, event.getChannel(), 60);
    }

    @Override
    public String getCommand() {
        return "activate";
    }

    @Override
    public String getBriefDescription() {
        return "Activates the bot";
    }

    @Override
    public String getSpecificDescription() {
        return "Activates the bot. Command takes no arguments, can only be executed by the owner. " +
                "Until the bot is activated in the guild, only the initializing commands are available.\n" +
                "*SYNOPSIS*:\n" +
                "`<prefix>activation [FLAG]`\n" +
                "*FLAGS*:\n" +
                "`-h` displays detailed help.";
    }
}
