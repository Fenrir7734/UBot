package com.fenrir.ubot.commands.general;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandCategory;
import com.fenrir.ubot.commands.CommandEvent;
import com.fenrir.ubot.util.CommandErrorsMsg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Dice extends Command {

    public Dice() {
        super();
        category = CommandCategory.GENERAL;
        maxNumberOfArguments = 1;
    }

    @Override
    public void execute(CommandEvent event) {

        if(!isCommandCorrect(event) || isHelpFlag(event)) {
            return;
        }

        String[] seq = {"1", "2", "3", "4", "5", "6"};

        try {
            if(event.getArgs().length == 1) {
                seq = getSings(event.getArgs()[0]);
            }

            sendBasicMessageToTextChannel("You rolled " + getRandom(seq), event.getChannel());
        } catch (NullPointerException e) {
            sendBasicMessageToTextChannel(CommandErrorsMsg.INVALID_ARGUMENTS, event.getChannel());
        }
    }

    private String[] getSings(String arg) {
        arg = arg.trim();
        String[] signs = null;

        if(arg.startsWith("[") && arg.endsWith("]") && arg.charAt(2) == '-' && arg.length() == 5) {
            int start = arg.charAt(1);
            int end = arg.charAt(arg.length() - 2);

            if(Character.isLetterOrDigit(start) && Character.isLetterOrDigit(end)) {
                signs = generateASCII(start, end);
            }
        } else if(arg.startsWith("{") && arg.endsWith("}")) {
            arg = arg.replace("}", "")
                    .replace("{", "");

            if(!arg.isBlank()) {
                signs = Arrays.stream(arg.split("[,;.]"))
                        .map(String::trim)
                        .toArray(String[]::new);
            }
        }

        return signs;
    }

    private static String[] generateASCII(int star, int end) {
        if(star > end) {
            int tmp = star;
            star = end;
            end = tmp;
        }

        ArrayList<String> sings = new ArrayList<>();

        while(star <= end) {
            if(Character.isLetterOrDigit((char) star)) {
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
        return " The command takes either 0 or 1 argument. " +
                "If no argument is given, the command randomizes from ranges `[1-6]`. " +
                "By giving an argument you can define the scope yourself. " +
                "The argument can only contain numbers and letters of the Latin alphabet.\n" +
                "The argument can take two forms:\n" +
                "`{a, b, c, d}` will randomize one of the given letters\n" +
                "`[a-d]` will randomize one letter from the given range\n";
    }
}
