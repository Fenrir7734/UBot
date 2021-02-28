package com.fenrir.ubot.commands.administration;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandCategory;
import com.fenrir.ubot.commands.CommandEvent;
import com.fenrir.ubot.config.Config;
import com.fenrir.ubot.utilities.*;
import com.fenrir.ubot.utilities.message.CommandErrorsMsg;
import com.fenrir.ubot.utilities.message.MessageCategory;
import com.fenrir.ubot.utilities.message.Messages;

public class Deactivation extends Command {

    public Deactivation() {
        super();
        category = CommandCategory.OWNER;
        minNumberOfArguments = 0;
        maxNumberOfArguments = 1;
    }

    @Override
    public void execute(CommandEvent event) {

        if (!CommandVerifier.isCommandCorrect(event, minNumberOfArguments, maxNumberOfArguments, flags)
                || !PermissionsVerifier.checkPermissions(botRequiredPermissions, event)
                || isHelpFlag(event)) {
            return;
        }

        if (!event.isOwner()) {
            Messages.sendEmbedMessage(CommandErrorsMsg.ONLY_OWNER, MessageCategory.ERROR, event.getChannel());
            return;
        }

        if (!Config.getConfig().isActive()) {
            Messages.sendEmbedMessage(Config.getConfig().getBotName() + " has already been deactivated!", MessageCategory.WARNING, event.getChannel());
            return;
        }

        Config.getConfig().setActive(false);
        Messages.sendEmbedMessage(Config.getConfig().getBotName() + "has been deactivated!", MessageCategory.INFO, event.getChannel());
    }

    @Override
    public String getCommand() {
        return "deactivation";
    }

    @Override
    public String getBriefDescription() {
        return "Deactivates the bot";
    }

    @Override
    public String getSpecificDescription() {
        return "Deactivates the bot. Command takes no arguments, can only be executed by the owner. " +
                "After executing this command, the bot becomes inactive and only initialization commands " +
                "will be available in guild.\n" +
                "*SYNOPSIS*:\n" +
                "`<prefix>deactivation [FLAG]`\n" +
                "*FLAGS*:\n" +
                "`-h` displays detailed help.";
    }
}
