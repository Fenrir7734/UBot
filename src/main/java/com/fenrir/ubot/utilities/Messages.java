package com.fenrir.ubot.utilities;

import com.fenrir.ubot.commands.Command;
import net.dv8tion.jda.api.entities.MessageChannel;

public class Messages {

    public static void sendBasicTextMessage(String message, MessageChannel channel) {
        channel.sendMessage(message).queue();
    }

    public static void sendBasicTextMessage(CommandErrorsMsg message, MessageChannel channel) {
        sendBasicTextMessage(message.getValue(), channel);
    }

    public static void sendBasicEmbedMessage(String message, MessageCategory category, MessageChannel channel) {
        channel.sendMessage(Embed.basicMessage(category.getValue(), message, category))
                .queue();
    }

    public static void sendBasicEmbedMessage(CommandErrorsMsg message, MessageCategory category, MessageChannel channel) {
        sendBasicEmbedMessage(message.getValue(), category, channel);
    }
//To nie działa, I tak ustawia tytuł jako to co się poda w MessageCategory
    public static void sendBasicEmbedMessage(String title, String message, MessageCategory category, MessageChannel channel) {
        channel.sendMessage(Embed.basicMessage(title, message, category))
                .queue();
    }

    public static void sendBasicEmbedMessage(String title, CommandErrorsMsg message, MessageCategory category, MessageChannel channel) {
        sendBasicEmbedMessage(title, message.getValue(), category, channel);
    }

    public static void sendHelpMessage(MessageChannel channel, Command command) {
        channel.sendMessage(Embed
                .commandHelpFormatting(command.getCommand(), command.getBriefDescription(), command.getSpecificDescription()))
                .queue();
    }

}
