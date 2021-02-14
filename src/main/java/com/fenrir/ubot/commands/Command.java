package com.fenrir.ubot.commands;

import com.fenrir.ubot.util.Embed;
import net.dv8tion.jda.api.entities.MessageChannel;

public abstract class Command {

    protected CommandCategory category;

    public abstract void execute(CommandEvent event);

    public abstract String getCommand();

    public abstract String getBriefDescription();

    public abstract String getSpecificDescription();

    protected void sendBasicMessageToTextChannel(String message, MessageChannel channel) {
        channel.sendMessage(message).queue();
    }

    protected void sendHelpMessageToTextChannel(MessageChannel channel) {
        channel.sendMessage(Embed
                        .commandHelpFormatting(getCommand(), getBriefDescription(), getSpecificDescription()))
                .queue();
    }
}
