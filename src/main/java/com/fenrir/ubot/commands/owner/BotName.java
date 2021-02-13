package com.fenrir.ubot.commands.owner;

import com.fenrir.ubot.UBot;
import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandEvent;
import com.fenrir.ubot.config.Config;
import com.fenrir.ubot.util.BasicMessages;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BotName extends Command {
    @Override
    public void execute(CommandEvent event) {

        if(!event.isOwner()) {
            sendBasicMessageToTextChannel(BasicMessages.ONLY_OWNER.name(), event.getEvent().getChannel());
            return;
        }

        if(event.getArgs().length < 1) {

        } else {
            String botId = event.getEvent().getJDA().getSelfUser().getId();
            event.getEvent().getJDA()
                    .getGuildById(event.getEvent().getGuild().getId())
                    .getMemberById(botId)
                    .modifyNickname(String.join(" ", event.getArgs()))
                    .queue();
            sendBasicMessageToTextChannel("Bot renamed!", event.getEvent().getChannel());
        }
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
