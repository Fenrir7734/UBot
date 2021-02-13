package com.fenrir.ubot.commands;

import com.fenrir.ubot.config.Config;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


public class CommandEvent {

    private final String prefix = Config.getConfig().getPrefix();
    private final MessageReceivedEvent event;
    private String command;
    private String[] args;
    private final String AuthorId;

    public CommandEvent(MessageReceivedEvent event) {
        this.event = event;
        this.AuthorId = event.getAuthor().getId();
        prepareMessage();
    }

    private void prepareMessage() {
        String tmp = event.getMessage().getContentRaw().replaceFirst(prefix, "");
        tmp = tmp.strip();

        String[] arr = tmp.split(" ");
        command = arr[0].toLowerCase();

        try {
            args = new String[arr.length - 1];

            System.arraycopy(arr, 1, args, 0, arr.length - 1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public MessageReceivedEvent getEvent() {
        return event;
    }

    public String getAuthorId() {
        return AuthorId;
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

    public boolean isOwner() {
        return event.getMessage().getAuthor().getId().equals(event.getGuild().getOwnerId());
    }


}
