package com.fenrir.ubot.commands.general;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandCategory;
import com.fenrir.ubot.commands.CommandEvent;
import com.fenrir.ubot.commands.CommandList;
import com.fenrir.ubot.utilities.*;
import net.dv8tion.jda.api.Permission;

import java.util.Collections;

public class Help extends Command {

    public Help() {
        super();
        category = CommandCategory.GENERAL;
        flags = new String[]{"-h", "-l"};
    }

    public void execute(CommandEvent event) {

        if (!CommandVerifier.isCommandCorrect(event, minNumberOfArguments, maxNumberOfArguments, flags)
                || !PermissionsVerifier.checkPermissions(botRequiredPermissions, event)
                || isHelpFlag(event)) {
            return;
        }

        if (event.getFlags().length == 0) {
            event.getChannel()
                    .sendMessage(Embed.basicHelpFormatting(CommandList.getCommandList().getCommandsByCategory()))
                    .queue();
        } else if (event.getFlags()[0].equals("-l") && event.getArgs().length == 0) {
            event.getChannel()
                    .sendMessage(Embed.listHelpFormatting(CommandList.getCommandList().getCommandsByCategory()))
                    .queue();
        } else if(event.getArgs().length > 0) {
            Messages.sendEmbedMessage(CommandErrorsMsg.TOO_MUCH_ARGUMENTS, MessageCategory.WARNING, event.getChannel());
        }
    }

    @Override
    public String getCommand() {
        return "help";
    }

    @Override
    public String getBriefDescription() {
        return "Lists all commands";
    }

    @Override
    public String getSpecificDescription() {
        return "Lists all commands available to the user. Command takes no arguments. " +
                "By default, it displays a list of commands with a short description. " +
                "This list may differ depending on the user's permissions.\n" +
                "*SYNOPSIS*:\n" +
                "`<prefix>help [FLAG]`\n" +
                "*FLAGS*:\n" +
                "`-h` displays detailed help.\n" +
                "`-l` command list without descriptions, divided into categories.";
    }
}
