package com.fenrir.ubot.utilities;

import com.fenrir.ubot.commands.CommandEvent;

import java.util.Arrays;

public class CommandVerifier {

    public static boolean isCommandCorrect(CommandEvent event, int minNumberOfArguments, int maxNumberOfArguments, String[] flags) {
        CommandErrorsMsg error;
        if ((error = checkCommand(event, minNumberOfArguments, maxNumberOfArguments, flags)) != null) {
            Messages.sendEmbedMessage(error, MessageCategory.ERROR, event.getChannel());
            return false;
        }
        return true;
    }

    private static CommandErrorsMsg checkCommand(CommandEvent event, int minNumberOfArguments, int maxNumberOfArguments, String[] flags) {
        String[] eventArgs = event.getArgs();
        String[] eventFlags = event.getFlags();

        if (eventArgs.length == 0 && eventFlags.length == 0) {
            return checkNumberOfArguments(eventArgs, minNumberOfArguments, maxNumberOfArguments);
        }

        boolean isFlag = eventFlags.length >= 1;

        if(eventFlags.length > 1) {
            return CommandErrorsMsg.TOO_MUCH_ARGUMENTS;
        }

        if (isFlag && !checkFlags(eventFlags[0], flags)) {
            return CommandErrorsMsg.INVALID_FLAG;
        }

        return isFlag ? null : checkNumberOfArguments(eventArgs, minNumberOfArguments, maxNumberOfArguments);
    }


    private static CommandErrorsMsg checkNumberOfArguments(String[] args, int minNumberOfArguments, int maxNumberOfArguments) {

        if (args.length < minNumberOfArguments) {
            return CommandErrorsMsg.TOO_FEW_ARGUMENTS;
        }

        if (args.length > maxNumberOfArguments) {
            return CommandErrorsMsg.TOO_MUCH_ARGUMENTS;
        }

        return null;
    }

    private static boolean checkFlags(String arg, String[] flags) {
        return Arrays.asList(flags).contains(arg);
    }

}
