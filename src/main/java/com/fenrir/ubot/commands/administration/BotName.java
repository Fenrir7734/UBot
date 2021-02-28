package com.fenrir.ubot.commands.administration;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandCategory;
import com.fenrir.ubot.commands.CommandEvent;
import com.fenrir.ubot.config.Config;
import com.fenrir.ubot.utilities.*;
import com.fenrir.ubot.utilities.message.CommandErrorsMsg;
import com.fenrir.ubot.utilities.message.MessageCategory;
import com.fenrir.ubot.utilities.message.Messages;

public class BotName extends Command {

    public BotName() {
        super();
        category = CommandCategory.OWNER;
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
            Messages.sendEmbedMessage(CommandErrorsMsg.ONLY_OWNER, MessageCategory.INFO, event.getChannel());
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

                Messages.sendEmbedMessage("Bot renamed!", MessageCategory.INFO, event.getChannel());
            } else {
                Messages.sendEmbedMessage("Nick name must be 32 or fewer in length.", MessageCategory.ERROR, event.getChannel());
            }
        } catch (NullPointerException e) {
            String message = "Error: renaming failed. " +
                    " Cannot find or rename a user with the given name. " +
                    " Check the log for more details.";
            Messages.sendEmbedMessage(message, MessageCategory.ERROR, event.getChannel());
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
        return "Changes bot nickname. Command takes one argument, can only be executed by the owner. " +
                "If the nickname consists of more than one word, it must be enclosed in single quotes, " +
                "otherwise an error will be returned.\n" +
                "*SYNOPSIS*:\n" +
                "`<prefix>botrename [FLAG | ARGUMENT | 'ARGUMENTS... ']`\n" +
                "*FLAGS*:\n" +
                "`-h` displays detailed help";
    }
}
