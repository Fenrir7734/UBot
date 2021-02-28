package com.fenrir.ubot.utilities.message;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandCategory;
import com.fenrir.ubot.config.Config;
import com.fenrir.ubot.utilities.imageUtil.ImageData;
import com.fenrir.ubot.utilities.message.MessageCategory;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Embed {

    public static MessageEmbed commandHelpFormatting(String command, String briefDescription, String specificDescription) {
        return new EmbedBuilder().setTitle("`" + Config.getConfig().getPrefix() + command + "`")
                .setColor(Config.getConfig().getHelpColor())
                .addField("Description", briefDescription, false)
                .addField("Specific Description", specificDescription, true)
                .build();
    }

    public static MessageEmbed basicHelpFormatting(HashMap<CommandCategory, ArrayList<Command>> commands) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("**Help**")
                .setColor(Config.getConfig().getHelpColor());

        for (CommandCategory category : commands.keySet()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Command command : commands.get(category)) {
                stringBuilder.append("`").append(Config.getConfig().getPrefix()).append(command.getCommand()).append("` ").append(command.getBriefDescription()).append("\n");
            }
            eb.addField(category.getValue(), stringBuilder.toString(), false);
        }

        return eb.build();
    }

    public static MessageEmbed listHelpFormatting(HashMap<CommandCategory, ArrayList<Command>> commands) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("**Command List**")
                .setColor(Config.getConfig().getHelpColor());

        for (CommandCategory category : commands.keySet()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Command command : commands.get(category)) {
                stringBuilder.append("`").append(Config.getConfig().getPrefix()).append(command.getCommand()).append("` ");
            }
            eb.addField(category.getValue(), stringBuilder.toString(), false);
        }

        return eb.build();
    }

    public static MessageEmbed listSubredditFormatting(String[] subreddits) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Meme")
                .setColor(validateMessageColor(MessageCategory.HELP));

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < subreddits.length; i++) {
            stringBuilder.append(i + 1).append(":\t").append(subreddits[i]).append("\n");
        }

        return eb.addField("Available subreddits", stringBuilder.toString(), false)
                .build();
    }

    public static MessageEmbed basicMessage(String title, String message, MessageCategory category) {
        return new EmbedBuilder()
                .setColor(validateMessageColor(category))
                .addField(title, message, false)
                .build();
    }

    public static MessageEmbed basicMessage(String message, MessageCategory category) {
        return basicMessage(category.getValue(), message, category);
    }

    public static MessageEmbed memeMessage(ImageData imageData) {
        return new EmbedBuilder()
                .setColor(validateMessageColor(MessageCategory.MESSAGE))
                .setTitle(imageData.getTitle())
                .setAuthor("u/" + imageData.getAuthor())
                .setImage(imageData.getUrl())
                .setFooter("r/" + imageData.getSource())
                .build();
    }

    private static Color validateMessageColor(MessageCategory category) {
        Color color = Config.getConfig().getMessageColor();
        switch (category) {
            case MESSAGE: color = Config.getConfig().getMessageColor(); break;
            case INFO: color = Config.getConfig().getInfoColor(); break;
            case WARNING: color = Config.getConfig().getWarningColor(); break;
            case ERROR: color = Config.getConfig().getErrorColor(); break;
            case HELP: color = Config.getConfig().getHelpColor(); break;
        }
        return color;
    }

}
