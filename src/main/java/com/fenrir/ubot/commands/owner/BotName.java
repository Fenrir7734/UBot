package com.fenrir.ubot.commands.owner;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandCategory;
import com.fenrir.ubot.commands.CommandEvent;
import com.fenrir.ubot.config.Config;
import com.fenrir.ubot.utilities.*;
import net.dv8tion.jda.api.Permission;

import java.util.Collections;

public class BotName extends Command {

    public BotName() {
        super();
        category = CommandCategory.OWNER;
        botRequiredPermissions = Collections.singletonList(Permission.MESSAGE_WRITE);
        minNumberOfArguments = 1;
        maxNumberOfArguments = 5;
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

        try {
            String botId = event.getEvent().getJDA().getSelfUser().getId();
            String newName = String.join(" ", event.getArgs());

            if (newName.length() <= 32) {
                event.getEvent().getJDA()
                        .getGuildById(event.getEvent().getGuild().getId())
                        .getMemberById(botId)
                        .modifyNickname(newName)
                        .queue();
                Config.getConfig().setBotName(newName);

                Messages.sendBasicEmbedMessage("Bot renamed!", MessageCategory.INFO, event.getChannel());
            } else {
                Messages.sendBasicEmbedMessage("Nick name must be 32 or fewer in length.", MessageCategory.ERROR, event.getChannel());
            }
        } catch (NullPointerException e) {
            String message = "Error: renaming failed. " +
                    " Cannot find or rename a user with the given name. " +
                    " Check the log for more details.";
            Messages.sendBasicEmbedMessage(message, MessageCategory.ERROR, event.getChannel());
        }
    }

    @Override
    public String getCommand() {
        return "botrename";
    }

    @Override
    public String getBriefDescription() {
        return "Changes the name of the bot";
    }

    @Override
    public String getSpecificDescription() {
        return null;
    }
}
