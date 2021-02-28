package com.fenrir.ubot.commands;

import com.fenrir.ubot.utilities.message.CommandErrorsMsg;
import com.fenrir.ubot.utilities.message.MessageCategory;
import com.fenrir.ubot.utilities.message.Messages;
import net.dv8tion.jda.api.Permission;

import java.util.Arrays;
import java.util.Collection;

public abstract class Command {

    protected CommandCategory category;

    protected Collection<Permission> userRequiredPermissions;
    protected Collection<Permission> botRequiredPermissions;

    protected int minNumberOfArguments;
    protected int maxNumberOfArguments;
    protected String[] flags;

    protected boolean isOnlyGuild;

    public Command() {
        userRequiredPermissions = null;
        botRequiredPermissions = Arrays.asList(Permission.MESSAGE_WRITE, Permission.MESSAGE_EMBED_LINKS);
        minNumberOfArguments = 0;
        maxNumberOfArguments = 0;
        flags = new String[]{"-h"};
        isOnlyGuild = true;
    }

    public abstract void execute(CommandEvent event);

    public abstract String getCommand();

    public abstract String getBriefDescription();

    public abstract String getSpecificDescription();

    public CommandCategory getCategory() {
        return category;
    }

    protected boolean isHelpFlag(CommandEvent event) {
        if (event.getFlags().length != 0 && event.getFlags()[0].equals("-h")) {
            if(event.getArgs().length == 0) {
                Messages.sendHelpMessage(event.getChannel(), this);
            } else {
                Messages.sendEmbedMessage(CommandErrorsMsg.TOO_MUCH_ARGUMENTS.getValue(),
                        MessageCategory.ERROR,
                        event.getChannel(),
                        30);
            }
            return true;
        }
        return false;
    }

    public boolean isOnlyGuild() {
        return isOnlyGuild;
    }

    public String[] getFlags() {
        return flags;
    }

    public int getMaxNumberOfArguments() {
        return maxNumberOfArguments;
    }

    public int getMinNumberOfArguments() {
        return minNumberOfArguments;
    }
}
