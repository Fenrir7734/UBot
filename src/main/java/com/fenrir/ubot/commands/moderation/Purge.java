package com.fenrir.ubot.commands.moderation;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandCategory;
import com.fenrir.ubot.commands.CommandEvent;
import com.fenrir.ubot.utilities.*;
import com.fenrir.ubot.utilities.message.MessageCategory;
import com.fenrir.ubot.utilities.message.Messages;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.Message;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Purge extends Command {

    public Purge() {
        super();
        category = CommandCategory.MODERATION;
        userRequiredPermissions = Collections.singletonList(Permission.MESSAGE_MANAGE);
        botRequiredPermissions = Arrays.asList(Permission.MESSAGE_MANAGE, Permission.MESSAGE_WRITE, Permission.MESSAGE_EMBED_LINKS, Permission.MESSAGE_READ);
        minNumberOfArguments = 1;
        maxNumberOfArguments = 10;
        flags = new String[]{"-h", "-a"};
    }

    @Override
    public void execute(CommandEvent event) {
        if(!CommandVerifier.isCommandCorrect(event, minNumberOfArguments, maxNumberOfArguments, flags)
        || !PermissionsVerifier.checkPermissions(userRequiredPermissions, botRequiredPermissions, event)
        || isHelpFlag(event)) {
            return;
        }

        if(event.getArgs().length == 0) {
            return;
        }

        String[] args = event.getArgs();

        int toDelete;
        String pattern = null;
        List<String> mentioned = null;

        try {
            toDelete = getNumberFromCommand(args[0]);
        } catch (NumberFormatException e) {
            Messages.sendEmbedMessage("First argument must be always a number.",
                    MessageCategory.ERROR,
                    event.getChannel(),
                    30);
            return;
        }

        switch (specifyArguments(args)) {
            case ONLY_NUMBER:
                break;
            case PATTERN:
                pattern = getPatternFromCommand(args[1]);
                break;
            case USER:
                mentioned = getMentionedIDFromCommand(event);
                break;
            case PATTERN_USER:
                pattern = getPatternFromCommand(args[1]);
                mentioned = getMentionedIDFromCommand(event);
                break;
            case INCORRECT:
                Messages.sendEmbedMessage("One or more arguments are not valid.",
                        MessageCategory.ERROR,
                        event.getChannel(),
                        30);
                return;
        }

        purge(event, toDelete, pattern, mentioned);

    }

    private void purge(CommandEvent event, int toDelete, String pattern, List<String> mentioned) {
        List<Message> messages = event.getChannel()
                .getHistory()
                .retrievePast(toDelete)
                .complete()
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if(pattern != null) {
            messages = messages.stream()
                    .filter(message -> message.getContentRaw().contains(pattern))
                    .collect(Collectors.toList());
        }

        if(mentioned != null) {
            messages = messages.stream()
                    .filter(message -> mentioned.contains(Objects.requireNonNull(message.getMember()).getId()))
                    .collect(Collectors.toList());
        }
        messages.forEach(message -> message.delete().queue());
    }

    private ArgumentType specifyArguments(String[] args) {

        if(args.length == 1) {
            return ArgumentType.ONLY_NUMBER;
        }

        if(args.length == 2) {
            if(args[1].startsWith("{") && args[1].endsWith("}")) {
                return ArgumentType.PATTERN;
            } else if(args[1].startsWith("<@!")) {
                return ArgumentType.USER;
            }
        }

        if(args[1].startsWith("{") && args[1].endsWith("}")) {
            for (int i = 2; i < args.length; i++) {
                if(!(args[i].startsWith("<@!") && args[i].endsWith(">"))) {
                    return ArgumentType.INCORRECT;
                }
            }
            return ArgumentType.PATTERN_USER;
        } else if(args[1].startsWith("<@!")){
            for (int i = 1; i < args.length; i++) {
                if(!(args[i].startsWith("<@!") && args[i].endsWith(">"))) {
                    return ArgumentType.INCORRECT;
                }
            }
            return ArgumentType.USER;
        }

        return ArgumentType.INCORRECT;
    }

    private int getNumberFromCommand(String arg) throws NumberFormatException {
        return Integer.parseInt(arg);
    }

    private String getPatternFromCommand(String arg) {
        return arg.replace("{", "")
                .replace("}", "");
    }

    private List<String> getMentionedIDFromCommand(CommandEvent event) {
        return event.getMessage().getMentionedMembers().stream()
                .map(ISnowflake::getId)
                .collect(Collectors.toList());
    }


    @Override
    public String getCommand() {
        return "purge";
    }

    @Override
    public String getBriefDescription() {
        return "Deletes messages in a text channel.";
    }

    @Override
    public String getSpecificDescription() {
        return "Deletes messages in the text channel on which this command was invoked. " +
                "By default, the command always takes the number of messages to be deleted as the " +
                "first argument and then you can enter a specific phrase after which messages to " +
                "be deleted will be searched and users whose messages will be deleted. " +
                "The phrase after which messages for deletion will be searched for must be between the brackets. " +
                "Message will be searched starting from the last one sent. " +
                "*SYNOPSIS*:\n" +
                "`<prefix>purge [FLAG] [ARGUMENT] [USER]`\n" +
                "*FLAGS*:\n" +
                "`-h` displays detailed help.\n" +
                "`-a` deletes all messages in the text channel. After typing the command " +
                "with this flag, it should be confirmed with the command `!confirm`. " +
                "The bot waits for confirmation for 30 seconds. I" +
                "f the command is not confirmed, no changes will be made.\n";
    }

    private enum ArgumentType {
        ONLY_NUMBER,
        PATTERN,
        USER,
        PATTERN_USER,
        INCORRECT
    }
}
