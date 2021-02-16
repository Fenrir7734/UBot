package com.fenrir.ubot.utilities;

import com.fenrir.ubot.commands.CommandEvent;

import java.util.Arrays;

public class CommandVerifier {

    public static boolean isCommandCorrect(CommandEvent event, int minNumberOfArguments, int maxNumberOfArguments, String[] flags) {
        CommandErrorsMsg error;
        if ((error = checkCommand(event.getArgs(), minNumberOfArguments, maxNumberOfArguments, flags)) != null) {
            Messages.sendBasicEmbedMessage(error, MessageCategory.ERROR, event.getChannel());
            return false;
        }
        return true;
    }

    private static CommandErrorsMsg checkCommand(String[] args, int minNumberOfArguments, int maxNumberOfArguments, String[] flags) {

        if (args.length == 0) {
            return checkNumberOfArguments(args, minNumberOfArguments, maxNumberOfArguments);
        }

        boolean isFlag = args[0].startsWith("-");

        if (isFlag && !checkFlags(args[0], flags)) {
            return CommandErrorsMsg.INVALID_FLAG;
        }

        if (isFlag && args.length > 1) {
            return CommandErrorsMsg.TOO_MUCH_ARGUMENTS;
        }

        return isFlag ? null : checkNumberOfArguments(args, minNumberOfArguments, maxNumberOfArguments);
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
