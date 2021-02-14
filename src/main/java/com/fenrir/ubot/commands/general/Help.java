package com.fenrir.ubot.commands.general;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandCategory;
import com.fenrir.ubot.commands.CommandEvent;

public class Help extends Command {

    public Help() {
        category = CommandCategory.GENERAL;
    }

    @Override
    public void execute(CommandEvent event) {

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
