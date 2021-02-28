package com.fenrir.ubot.commands.general;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandCategory;
import com.fenrir.ubot.commands.CommandEvent;
import com.fenrir.ubot.exception.ImageException;
import com.fenrir.ubot.utilities.*;
import com.fenrir.ubot.utilities.imageUtil.ImageData;
import com.fenrir.ubot.utilities.imageUtil.RedditScraper;
import com.fenrir.ubot.utilities.message.CommandErrorsMsg;
import com.fenrir.ubot.utilities.message.MessageCategory;
import com.fenrir.ubot.utilities.message.Messages;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.dv8tion.jda.api.Permission;
import org.json.JSONException;

import java.util.Arrays;
import java.util.Collections;

public class Meme extends Command {

    private final SubredditContainer subreddits;

    public Meme() {
        super();
        category = CommandCategory.GENERAL;
        userRequiredPermissions = Collections.singletonList(Permission.MESSAGE_ATTACH_FILES);
        botRequiredPermissions = Arrays.asList(Permission.MESSAGE_WRITE, Permission.MESSAGE_EMBED_LINKS, Permission.MESSAGE_ATTACH_FILES);
        minNumberOfArguments = 0;
        maxNumberOfArguments = 1;
        flags = new String[]{"-h", "-l"};

        subreddits = new SubredditContainer();
    }

    @Override
    public void execute(CommandEvent event) {
        event.getMessage().delete().queue();

        if (!CommandVerifier.isCommandCorrect(event, minNumberOfArguments, maxNumberOfArguments, flags)
                || !PermissionsVerifier.checkPermissions(userRequiredPermissions, botRequiredPermissions, event)
                || isHelpFlag(event)) {
            return;
        }

        if(event.getFlags().length == 1 && event.getFlags()[0].equals("-l")) {
            Messages.sendList(subreddits.getSubreddits(), event.getChannel());
            return;
        }

        String source = subreddits.getDefaultSubreddit();

        if(event.getArgs().length > 0) {
            source = parseArgument(event);

            if(source == null) {
                return;
            }
        }

        try {
            ImageData imageData = RedditScraper.getInstance().getMeme(source);
            Messages.sendImage(imageData, event.getChannel());
        } catch (ImageException e) {
            Messages.sendEmbedMessage(e.getMessage(), MessageCategory.WARNING, event.getChannel());
        } catch (JSONException | UnirestException e) {
            Messages.sendEmbedMessage("An error has occurred",
                    MessageCategory.ERROR,
                    event.getChannel(),
                    30);
        }
    }

    private String parseArgument(CommandEvent event) {
        if(event.getArgs().length > 1) {
            Messages.sendEmbedMessage(CommandErrorsMsg.TOO_MUCH_ARGUMENTS,
                    MessageCategory.ERROR,
                    event.getChannel(),
                    30);
            return null;
        }

        String arg = event.getArgs()[0];
        String subreddit = null;

        if(Utilities.isInteger(arg)) {
            if((subreddit = subreddits.getSubreddit(Integer.parseInt(arg))) == null) {
                Messages.sendEmbedMessage("Number out of range",
                        MessageCategory.ERROR,
                        event.getChannel(),
                        30);
            }
        } else if(Utilities.isNumeric(arg)) {
            Messages.sendEmbedMessage("The number should be integer.",
                    MessageCategory.ERROR,
                    event.getChannel(),
                    30);
        } else if((subreddit = subreddits.getSubreddit(arg)) == null) {
            Messages.sendEmbedMessage("Incorrect name of subreddit",
                    MessageCategory.ERROR,
                    event.getChannel(),
                    30);
        }

        return subreddit;
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
                "However, this behavior can be changed by giving the name of one of the available subreddits. " +
                "The memes are randomly selected from the today top 100 posts.\n" +
                "*SYNOPSIS*:\n" +
                "`<prefix>meme [FLAG] [ARGUMENT]`\n" +
                "*FLAGS*:\n" +
                "`-h` displays detailed help.\n" +
                "`-l` lists all available subreddits.\n";
    }

    private static class SubredditContainer {

        private String[] subreddits;

        public SubredditContainer() {
            initSubreddits();
        }

        public String[] getSubreddits() {
            return subreddits;
        }

        public String getSubreddit(int i) {
            i = i - 1;
            if(i >= 0 && i < subreddits.length) {
                return subreddits[i];
            }
            return null;
        }

        public String getSubreddit(String subreddit) {
            subreddit = subreddit.toLowerCase().trim();

            for (String s : subreddits) {
                if (s.equalsIgnoreCase(subreddit)) {
                    return s;
                }
            }
            return null;
        }

        public String getDefaultSubreddit() {
            return subreddits[0];
        }

        private void initSubreddits() {
            subreddits = new String[]{
                    "memes",
                    "meme",
                    "wholesomememes",
                    "starterpacks",
                    "dankmemes",
                    "meirl",
                    "me_irl",
                    "anime_irl",
                    "MEOW_IRL",
                    "2meirl4meirl",
                    "trippinthroughtime",
                    "boottoobig",
                    "BlackPeopleTwitter",
                    "ProgrammerHumor",
                    "linuxmemes",
                    "HistoryMemes",
                    "MemeEconomy"
            };
        }
    }
}
