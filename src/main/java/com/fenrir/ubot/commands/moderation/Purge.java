package com.fenrir.ubot.commands.moderation;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandCategory;
import com.fenrir.ubot.commands.CommandEvent;
import net.dv8tion.jda.api.Permission;

import java.util.Arrays;
import java.util.Collections;

public class Purge extends Command {

    public Purge() {
        super();
        category = CommandCategory.MODERATION;
        userRequiredPermissions = Collections.singletonList(Permission.MESSAGE_MANAGE);
        botRequiredPermissions = Arrays.asList(Permission.MESSAGE_MANAGE, Permission.MESSAGE_WRITE, Permission.MESSAGE_EMBED_LINKS, Permission.MESSAGE_READ);
        minNumberOfArguments = 1;
        maxNumberOfArguments = 1;
    }

    @Override
    public void execute(CommandEvent event) {

    }

    @Override
    public String getCommand() {
        return null;
    }

    @Override
    public String getBriefDescription() {
        return null;
    }

    @Override
    public String getSpecificDescription() {
        return null;
    }
}
