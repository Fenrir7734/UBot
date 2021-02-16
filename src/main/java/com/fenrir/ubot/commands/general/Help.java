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
        botRequiredPermissions = Collections.singletonList(Permission.MESSAGE_WRITE);
        flags = new String[]{"-h", "-l"};
    }

    public void execute(CommandEvent event) {

        if (!CommandVerifier.isCommandCorrect(event, minNumberOfArguments, maxNumberOfArguments, flags)
                || !PermissionsVerifier.checkPermissions(botRequiredPermissions, event)
                || isHelpFlag(event)) {
            return;
        }

        if (event.getArgs().length == 0) {
            event.getChannel()
                    .sendMessage(Embed.basicHelpFormatting(CommandList.getCommandList().getCommandsByCategory()))
                    .queue();
        } else if (event.getArgs()[0].equals("-l")) {
            event.getChannel()
                    .sendMessage(Embed.listHelpFormatting(CommandList.getCommandList().getCommandsByCategory()))
                    .queue();
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
        return null;
    }
}
