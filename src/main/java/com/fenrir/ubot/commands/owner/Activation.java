package com.fenrir.ubot.commands.owner;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandCategory;
import com.fenrir.ubot.commands.CommandEvent;
import com.fenrir.ubot.config.Config;
import com.fenrir.ubot.util.CommandErrorsMsg;

public class Activation extends Command {

    public Activation() {
        super();
        category = CommandCategory.OWNER;
    }

    @Override
    public void execute(CommandEvent event) {

        if(!isCommandCorrect(event) || isHelpFlag(event)) {
            return;
        }

        if(!event.isOwner()) {
            sendBasicMessageToTextChannel(CommandErrorsMsg.ONLY_OWNER, event.getChannel());
            return;
        }

        if(Config.getConfig().isActive()) {
            sendBasicMessageToTextChannel(Config.getConfig().getBotName() + " has already been activated!", event.getChannel());
            return;
        }

        Config.getConfig().setActive(true);
        sendBasicMessageToTextChannel(Config.getConfig().getBotName() + " has been activated!", event.getChannel());
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
