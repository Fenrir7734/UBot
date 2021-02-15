package com.fenrir.ubot.commands;

import com.fenrir.ubot.util.CommandErrorsMsg;
import com.fenrir.ubot.util.Embed;
import net.dv8tion.jda.api.entities.MessageChannel;

public abstract class Command {

    protected CommandCategory category;
    protected int minNumberOfArguments;
    protected int maxNumberOfArguments;
    protected String[] flags;

    public Command() {
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

    protected boolean isCommandCorrect(CommandEvent event) {
        CommandErrorsMsg error;
        if ((error = event.checkCommand(minNumberOfArguments, maxNumberOfArguments, flags)) != null) {
            sendBasicMessageToTextChannel(error, event.getChannel());
            return false;
        }
        return true;
    }

    protected boolean isHelpFlag(CommandEvent event) {
        if(event.getArgs().length != 0 && event.getArgs()[0].equals("-h")) {
            sendHelpMessageToTextChannel(event.getChannel());
            return true;
        }
        return false;
    }

    protected void sendBasicMessageToTextChannel(String message, MessageChannel channel) {
        channel.sendMessage(message).queue();
    }

    protected void sendBasicMessageToTextChannel(CommandErrorsMsg message, MessageChannel channel) {
        sendBasicMessageToTextChannel(message.getValue(), channel);
    }

    protected void sendHelpMessageToTextChannel(MessageChannel channel) {
        channel.sendMessage(Embed
                        .commandHelpFormatting(getCommand(), getBriefDescription(), getSpecificDescription()))
                .queue();
    }

}
