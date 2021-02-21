package com.fenrir.ubot.commands.administration;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandCategory;
import com.fenrir.ubot.commands.CommandEvent;
import com.fenrir.ubot.config.Config;
import com.fenrir.ubot.utilities.*;
import net.dv8tion.jda.api.Permission;

import java.util.Collections;

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
            Messages.sendBasicEmbedMessage(CommandErrorsMsg.ONLY_OWNER, MessageCategory.ERROR, event.getChannel());
            return;
        }

        if (!Config.getConfig().isActive()) {
            Messages.sendBasicEmbedMessage(Config.getConfig().getBotName() + " has already been deactivated!", MessageCategory.WARNING, event.getChannel());
            return;
        }

        Config.getConfig().setActive(false);
        Messages.sendBasicEmbedMessage(Config.getConfig().getBotName() + "has been deactivated!", MessageCategory.INFO, event.getChannel());
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
        return null;
    }
}
