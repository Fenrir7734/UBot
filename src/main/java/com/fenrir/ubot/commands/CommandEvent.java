package com.fenrir.ubot.commands;

import com.fenrir.ubot.config.Config;
import com.fenrir.ubot.util.BasicMessages;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;


public class CommandEvent {

    private final String prefix = Config.getConfig().getPrefix();
    private final MessageReceivedEvent event;
    private final MessageChannel channel;
    private String command;
    private String[] args;
    private final String AuthorId;

    public CommandEvent(MessageReceivedEvent event) {
        this.event = event;
        AuthorId = event.getAuthor().getId();
        channel = event.getChannel();
        prepareMessage();
    }

    private void prepareMessage() {

        String[] arr = Arrays.stream(event.getMessage().getContentRaw()
                    .replaceFirst(prefix, "")
                    .strip()
                    .split(" (?![^{]*})"))    //arguments are split by " ". It ignores what is between brackets.
                .map(String::trim)
                .toArray(String[]::new);

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

    public MessageChannel getChannel() {
        return channel;
    }

    public boolean isOwner() {
        return event.getMessage().getAuthor().getId().equals(event.getGuild().getOwnerId());
    }

    public String checkNumberOfArguments(String[] args, int minArgumentNumber, boolean canBeLess, boolean canBeMore) {

        if(args.length < minArgumentNumber && !canBeLess) {
            return BasicMessages.TOO_FEW_ARGUMENTS.getValue();
        }

        if(args.length > minArgumentNumber && !canBeMore) {
            return BasicMessages.TOO_MUCH_ARGUMENTS.getValue();
        }

        return null;
    }


}
