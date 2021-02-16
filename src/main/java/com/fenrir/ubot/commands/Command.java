package com.fenrir.ubot.commands;

import com.fenrir.ubot.utilities.Messages;
import net.dv8tion.jda.api.Permission;

import java.util.Collection;

public abstract class Command {

    protected CommandCategory category;

    protected Collection<Permission> userRequiredPermissions;
    protected Collection<Permission> botRequiredPermissions;

    protected int minNumberOfArguments;
    protected int maxNumberOfArguments;
    protected String[] flags;

    public Command() {
        userRequiredPermissions = null;
        minNumberOfArguments = 0;
        maxNumberOfArguments = 0;
        flags = new String[]{"-h"};
    }

    public abstract void execute(CommandEvent event);

    public abstract String getCommand();

    public abstract String getBriefDescription();

    public abstract String getSpecificDescription();

    public CommandCategory getCategory() {
        return category;
    }

    protected boolean isHelpFlag(CommandEvent event) {
        if (event.getArgs().length != 0 && event.getArgs()[0].equals("-h")) {
            Messages.sendHelpMessage(event.getChannel(), this);
            return true;
        }
        return false;
    }

}
