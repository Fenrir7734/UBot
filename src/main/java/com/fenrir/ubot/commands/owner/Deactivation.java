package com.fenrir.ubot.commands.owner;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandEvent;
import com.fenrir.ubot.config.Config;
import com.fenrir.ubot.util.BasicMessages;

public class Deactivation extends Command {
    @Override
    public void execute(CommandEvent event) {
        String message;

        if(!event.isOwner()) {
            message = BasicMessages.ONLY_OWNER.name();
        } else if(!Config.getConfig().isActive()) {
            message = Config.getConfig().getBotName() + " has already been deactivated!";
        } else {
            Config.getConfig().setActive(false);
            message = Config.getConfig().getBotName() + "has been deactivated!";
        }
        sendBasicMessageToTextChannel(message, event.getEvent().getChannel());
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
