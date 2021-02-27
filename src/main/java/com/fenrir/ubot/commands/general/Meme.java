package com.fenrir.ubot.commands.general;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandCategory;
import com.fenrir.ubot.commands.CommandEvent;
import com.fenrir.ubot.utilities.*;
import net.dv8tion.jda.api.Permission;

import java.util.Arrays;
import java.util.Collections;

public class Meme extends Command {

    public Meme() {
        super();
        category = CommandCategory.GENERAL;
        userRequiredPermissions = Collections.singletonList(Permission.MESSAGE_ATTACH_FILES);
        botRequiredPermissions = Arrays.asList(Permission.MESSAGE_WRITE, Permission.MESSAGE_EMBED_LINKS, Permission.MESSAGE_ATTACH_FILES);
        minNumberOfArguments = 0;
        maxNumberOfArguments = 1;
        flags = new String[]{"-h", "-r"};
    }

    @Override
    public void execute(CommandEvent event) {
        if (!CommandVerifier.isCommandCorrect(event, minNumberOfArguments, maxNumberOfArguments, flags)
                || !PermissionsVerifier.checkPermissions(userRequiredPermissions, botRequiredPermissions, event)
                || isHelpFlag(event)) {
            return;
        }

    }

    @Override
    public String getCommand() {
        return "meme";
    }

    @Override
    public String getBriefDescription() {
        return "Sends a random meme.";
    }

    @Override
    public String getSpecificDescription() {
        return "Sends a random meme to a text channel. By default, memes are downloaded from r/memes. " +
                "However, this behavior can be changed by setting the appropriate flag. " +
                "The memes are randomly selected from the today top 100 posts.\n" +
                "*SYNOPSIS*:\n" +
                "`<prefix>meme [FLAG] ['ARGUMENT']`\n" +
                "*FLAGS*:\n" +
                "`-h` displays detailed help.\n" +
                "`-r` this flag allows to set any subreddit. The name of subreddit from which we want to " +
                "draw the meme should be enclosed in brackets and preceded by `r/`. " +
                "For example `<prefix>meme -r {r/memes}`";
    }
}
