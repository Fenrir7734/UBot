package com.fenrir.ubot.commands.moderation;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandCategory;
import com.fenrir.ubot.commands.CommandEvent;
import com.fenrir.ubot.utilities.CommandVerifier;
import com.fenrir.ubot.utilities.PermissionsVerifier;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import org.apache.logging.log4j.core.net.TcpSocketManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Purge extends Command {

    private int toDelete;
    private String pattern;
    private List<Member> mentioned;

    private List<Message> history;

    public Purge() {
        super();
        category = CommandCategory.MODERATION;
        userRequiredPermissions = Collections.singletonList(Permission.MESSAGE_MANAGE);
        botRequiredPermissions = Arrays.asList(Permission.MESSAGE_MANAGE, Permission.MESSAGE_WRITE, Permission.MESSAGE_EMBED_LINKS, Permission.MESSAGE_READ);
        minNumberOfArguments = 1;
        maxNumberOfArguments = 10;
        flags = new String[]{"-h", "-n", "-a"};
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

        //String flag = event.getFlags()[0];
        //System.out.println(event.getMessage().getMentionedMembers());
        //System.out.println(event.getMessage().getContentRaw());

        System.out.println(checkCommandStructure(event));
        System.out.println(toDelete);
        System.out.println(pattern);
        System.out.println(mentioned.toString());
        toDelete = 0;
        pattern = "";
        mentioned = null;
    }

    private void purge(CommandEvent event) {
        String[] args = event.getArgs();

        int toDelete = 0;
        String pattern = "";
        List<Member> mentioned = null;



        if(event.getArgs().length > 0) {
            try {
                toDelete = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println(e);
                return;
            }

            if(args.length > 1 && args[1].startsWith("'") && args[1].endsWith("'")) {
                pattern = event.getArgs()[1];
            } else if(args.length > 1 && args[1].startsWith("'")) {
                return;
            } else if(args.length > 1 && args[1].endsWith("'")) {
                return;
            }

            mentioned = event.getMessage().getMentionedMembers();
        }

        System.out.println(pattern);
        System.out.println(mentioned);

        try {
            event.getChannel()
                    .getHistory()
                    .retrievePast(toDelete)
                    .complete()
                    .forEach(message -> message.delete().queue());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean checkCommandStructure(CommandEvent event) {
        String[] args = event.getArgs();

        try {
            toDelete = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            return false;
        }

        if(args.length == 1) {
            return true;
        }
        int i = 1;
        if(args[1].startsWith("'")) {
            if(args[1].endsWith("'")) {
                pattern = args[1];
            } else {
                return false;
            }
            i = 2;
        }

        while(i < args.length) {
            if(!args[i].startsWith("<@!")) {
                return false;
            }
            i++;
        }

        mentioned = event.getMessage().getMentionedMembers();

        return true;
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
                "By setting the appropriate flags, the command can delete a certain number " +
                "of messages starting from the last one sent or clear the entire text channel.\n" +
                "*SYNOPSIS*:\n" +
                "`<prefix>purge [FLAG] [ARGUMENT] [USER | ROLE]`\n" +
                "*FLAGS*:\n" +
                "`-h` displays detailed help.\n" +
                "`-n` deletes the specified number of messages starting from the last sent. " +
                "This flag must always be followed by an argument specifying the number of messages to be deleted.\n" +
                "`-a` deletes all messages in the text channel. After typing the command " +
                "with this flag, it should be confirmed with the command `!confirm`. " +
                "The bot waits for confirmation for 30 seconds. I" +
                "f the command is not confirmed, no changes will be made.\n";
    }
}
