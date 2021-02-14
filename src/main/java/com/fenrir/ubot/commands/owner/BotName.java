package com.fenrir.ubot.commands.owner;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandCategory;
import com.fenrir.ubot.commands.CommandEvent;
import com.fenrir.ubot.config.Config;
import com.fenrir.ubot.util.BasicMessages;

public class BotName extends Command {

    public BotName() {
        category = CommandCategory.OWNER;
    }

    @Override
    public void execute(CommandEvent event) {

        if(!event.isOwner()) {
            sendBasicMessageToTextChannel(BasicMessages.ONLY_OWNER.getValue(), event.getChannel());
            return;
        }

        String message;

        if((message = event.checkNumberOfArguments(event.getArgs(), 1, false, true)) == null) {
            try {
                String botId = event.getEvent().getJDA().getSelfUser().getId();
                String newName = String.join(" ", event.getArgs());

                if(newName.length() <= 32) {
                    event.getEvent().getJDA()
                            .getGuildById(event.getEvent().getGuild().getId())
                            .getMemberById(botId)
                            .modifyNickname(newName)
                            .queue();
                    Config.getConfig().setBotName(newName);

                    message = "Bot renamed!";
                } else {
                    message = "Nick name must be 32 or fewer in length.";
                }
            } catch (NullPointerException e) {
                message = "Error: renaming failed. " +
                        " Cannot find or rename a user with the given name. " +
                        " Check the log for more details.";
            }
        }

        sendBasicMessageToTextChannel(message, event.getChannel());
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
        return null;
    }
}
