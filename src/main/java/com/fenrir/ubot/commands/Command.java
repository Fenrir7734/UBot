package com.fenrir.ubot.commands;

import com.fenrir.ubot.util.BasicMessages;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.nio.channels.Channel;


public abstract class Command {

    public abstract void execute(CommandEvent event);

    public abstract String getCommand();

    public abstract String getBriefDescription();

    public abstract String getSpecificDescription();

    protected void sendBasicMessageToTextChannel(String message, MessageChannel channel) {
        channel.sendMessage(message).queue();
    }
}
