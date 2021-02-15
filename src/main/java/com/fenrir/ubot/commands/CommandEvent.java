package com.fenrir.ubot.commands;

import com.fenrir.ubot.config.Config;
import com.fenrir.ubot.util.CommandErrorsMsg;
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
                    .split(" (?![^{]*})"))    //arguments are split by " ". Contents of brackets is ignored.
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

    private CommandErrorsMsg checkNumberOfArguments(int minNumberOfArguments, int maxNumberOfArguments) {

        if(args.length < minNumberOfArguments) {
            return CommandErrorsMsg.TOO_FEW_ARGUMENTS;
        }

        if(args.length > maxNumberOfArguments) {
            return CommandErrorsMsg.TOO_MUCH_ARGUMENTS;
        }

        return null;
    }

    private boolean checkFlags(String arg, String[] flags) {
        return Arrays.asList(flags).contains(arg);
    }

    /* It is only temporary
    public CommandErrorsMsg checkCommand(int minNumberOfArguments, int maxNumberOfArguments, String[] flags) {
        if(args.length > 0) {
            if(args[0].startsWith("-")) {
                if(checkFlags(args[0], flags) && args.length == 1) {
                    return null;
                } else {
                    if(args.length != 1) {
                        return CommandErrorsMsg.TOO_MUCH_ARGUMENTS;
                    } else {
                        return CommandErrorsMsg.INVALID_FLAG;
                    }
                }
            }
        }
        return checkNumberOfArguments(minNumberOfArguments, maxNumberOfArguments);
    }
    */

    public CommandErrorsMsg checkCommand(int minNumberOfArguments, int maxNumberOfArguments, String[] flags) {

        if(args.length == 0) {
            return checkNumberOfArguments(minNumberOfArguments, maxNumberOfArguments);
        }

        boolean isFlag = args[0].startsWith("-");

        if(isFlag && !checkFlags(args[0], flags)) {
            return CommandErrorsMsg.INVALID_FLAG;
        }

        if(isFlag && args.length > 1) {
            return CommandErrorsMsg.TOO_MUCH_ARGUMENTS;
        }

        return isFlag ? null : checkNumberOfArguments(minNumberOfArguments, maxNumberOfArguments);
    }

}
