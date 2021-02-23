package com.fenrir.ubot.commands.general;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandCategory;
import com.fenrir.ubot.commands.CommandEvent;
import com.fenrir.ubot.utilities.*;
import net.dv8tion.jda.api.Permission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Roll extends Command {

    public Roll() {
        super();
        category = CommandCategory.GENERAL;
        maxNumberOfArguments = 1;
        isOnlyGuild = false;
    }

    @Override
    public void execute(CommandEvent event) {
        if (!CommandVerifier.isCommandCorrect(event, minNumberOfArguments, maxNumberOfArguments, flags)
                || !PermissionsVerifier.checkPermissions(botRequiredPermissions, event)
                || isHelpFlag(event)) {
            return;
        }

        String[] seq = {"1", "2", "3", "4", "5", "6"};

        try {
            if (event.getArgs().length == 1) {
                seq = getSings(event.getArgs()[0]);
            }

            Messages.sendMessage("You rolled " + getRandom(seq), event.getChannel());
        } catch (NullPointerException e) {
            Messages.sendEmbedMessage(CommandErrorsMsg.INVALID_ARGUMENTS, MessageCategory.ERROR, event.getChannel());
        }
    }

    private String[] getSings(String arg) {
        arg = arg.trim();
        String[] signs = null;

        if (arg.startsWith("[") && arg.endsWith("]") && arg.charAt(2) == '-' && arg.length() == 5) {
            int start = arg.charAt(1);
            int end = arg.charAt(arg.length() - 2);

            if (Character.isLetterOrDigit(start) && Character.isLetterOrDigit(end)) {
                signs = generateASCII(start, end);
            }
        } else if (arg.startsWith("{") && arg.endsWith("}")) {
            arg = arg.replace("}", "")
                    .replace("{", "");

            if (!arg.isBlank()) {
                signs = Arrays.stream(arg.split("[,;.]"))
                        .map(String::trim)
                        .toArray(String[]::new);
            }
        }

        return signs;
    }

    private static String[] generateASCII(int star, int end) {
        if (star > end) {
            int tmp = star;
            star = end;
            end = tmp;
        }

        ArrayList<String> sings = new ArrayList<>();

        while (star <= end) {
            if (Character.isLetterOrDigit((char) star)) {
                sings.add(String.valueOf((char) star));
            }
            star++;
        }
        return sings.toArray(new String[0]);
    }

    private String getRandom(String[] signSeq) throws NullPointerException {
        return signSeq[new Random().nextInt(signSeq.length)];
    }

    @Override
    public String getCommand() {
        return "roll";
    }

    @Override
    public String getBriefDescription() {
        return "Roll the dice";
    }

    @Override
    public String getSpecificDescription() {
        return  "The command takes either 0 or 1 argument. If no argument is given, " +
                "the command randomizes from ranges `[1-6]`. By giving an argument you " +
                "can define the scope yourself.The argument can take two forms:\n" +
                "`{a, b, c, d}` will randomize one of the given signs or words.\n" +
                "`[a-d]` will randomize one letter from the given range. Scope can only " +
                "contain numbers and letters of the Latin alphabet. The order of characters " +
                "is the same as in the ASCII standard.\n" +
                "*SYNOPSIS*:\n" +
                "`<prefix>roll [FLAG | ARGUMENT]`\n" +
                "*FLAGS*:\n" +
                "`-h` displays detailed help.";
    }
}
