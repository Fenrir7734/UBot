package com.fenrir.ubot.commands.administration;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandCategory;
import com.fenrir.ubot.commands.CommandEvent;
import com.fenrir.ubot.config.Config;
import com.fenrir.ubot.utilities.*;
import net.dv8tion.jda.api.Permission;

import java.util.Collections;

public class Activation extends Command {

    public Activation() {
        super();
        category = CommandCategory.OWNER;
        botRequiredPermissions = Collections.singletonList(Permission.MESSAGE_WRITE);
    }

    @Override
    public void execute(CommandEvent event) {

        if (!CommandVerifier.isCommandCorrect(event, minNumberOfArguments, maxNumberOfArguments, flags)
                || !PermissionsVerifier.checkPermissions(botRequiredPermissions, event)
                || isHelpFlag(event)) {
            return;
        }

        if (!event.isOwner()) {
            Messages.sendBasicTextMessage(CommandErrorsMsg.ONLY_OWNER, event.getChannel());
            return;
        }

        if (Config.getConfig().isActive()) {
            Messages.sendBasicEmbedMessage(Config.getConfig().getBotName() + " has already been activated!", MessageCategory.WARNING, event.getChannel());
            return;
        }

        Config.getConfig().setActive(true);
        Messages.sendBasicEmbedMessage(Config.getConfig().getBotName() + " has been activated!", MessageCategory.INFO, event.getChannel());
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
        return null;
    }
}
