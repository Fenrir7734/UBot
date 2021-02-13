package com.fenrir.ubot.commands.owner;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandEvent;
import com.fenrir.ubot.config.Config;
import com.fenrir.ubot.util.BasicMessages;

public class Activation extends Command {
    @Override
    public void execute(CommandEvent event) {
        String message;

        if(!event.isOwner()) {
            message = BasicMessages.ONLY_OWNER.name();
        } else if(Config.getConfig().isActive()) {
            message = Config.getConfig().getBotName() + " has already been activated!";
        } else {
            Config.getConfig().setActive(true);
            message = Config.getConfig().getBotName() + "has been activated!";
        }
        sendBasicMessageToTextChannel(message, event.getEvent().getChannel());
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
