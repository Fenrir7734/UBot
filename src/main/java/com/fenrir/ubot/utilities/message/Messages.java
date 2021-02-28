package com.fenrir.ubot.utilities.message;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.utilities.imageUtil.ImageData;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class Messages {

    public static void sendMessage(String message, MessageChannel channel) {
        channel.sendMessage(message).queue();
    }

    public static void sendMessage(String message, MessageChannel channel, int delay) {
        channel.sendMessage(message)
                .queue(newMessage -> newMessage.delete()
                        .queueAfter(delay, TimeUnit.SECONDS));
    }

    public static void sendEmbedMessage(String title, String message, MessageCategory category, MessageChannel channel) {
        channel.sendMessage(Embed.basicMessage(title, message, category)).queue();
    }

    public static void sendEmbedMessage(String message, MessageCategory category, MessageChannel channel) {
        sendEmbedMessage(category.getValue(), message, category, channel);
    }

    public static void sendEmbedMessage(CommandErrorsMsg message, MessageCategory category, MessageChannel channel) {
        sendEmbedMessage(category.getValue(), message.getValue(), category, channel);
    }

    public static void sendEmbedMessage(String title, String message, MessageCategory category, MessageChannel channel, int delay) {
        channel.sendMessage(Embed.basicMessage(title, message, category))
                .queue(newMessage -> newMessage.delete()
                        .queueAfter(delay, TimeUnit.SECONDS));
    }

    public static void sendEmbedMessage(String message, MessageCategory category, MessageChannel channel, int delay) {
        sendEmbedMessage(category.getValue(), message, category, channel, delay);
    }

    public static void sendEmbedMessage(CommandErrorsMsg message, MessageCategory category, MessageChannel channel, int delay) {
        sendEmbedMessage(category.getValue(), message.getValue(), category, channel, delay);
    }

    public static void sendHelpMessage(MessageChannel channel, Command command) {
        channel.sendMessage(Embed
                .commandHelpFormatting(command.getCommand(), command.getBriefDescription(), command.getSpecificDescription()))
                .queue(newMessage -> newMessage.delete()
                        .queueAfter(2, TimeUnit.MINUTES));
    }


    public static void sendImage(ImageData imageData, MessageChannel channel) {
        channel.sendMessage(Embed.memeMessage(imageData)).queue();
    }

    public static void sendList(String[] list, MessageChannel channel) {
        channel.sendMessage(Embed.listSubredditFormatting(list))
                .queue(newMessage -> newMessage.delete()
                        .queueAfter(2, TimeUnit.MINUTES));
    }

    public static void sendTextPrivateMessage(String message, User target) {
            target.openPrivateChannel()
                    .queue(privateChannel ->
                            privateChannel.sendMessage(message)
                                    .queue());
    }

    public static void sendEmbedPrivateMessage(String message, MessageCategory category, User target) {
        target.openPrivateChannel()
                .queue(privateChannel ->
                        privateChannel.sendMessage(Embed.basicMessage(message, category))
                                .queue());
    }

    public static void sendErrorSendingPermissionMessages(MessageReceivedEvent event) {
        if(!(event.getChannel() instanceof TextChannel)) {
            return;
        }

        Member selfMember = event.getGuild().getSelfMember();
        TextChannel channel = (TextChannel) event.getChannel();
        String messageToSend = "";

        boolean hasGuildMessageWritePerms = selfMember.hasPermission(Permission.MESSAGE_WRITE);
        boolean hasGuildSendEmbedLinksPerms = selfMember.hasPermission(Permission.MESSAGE_EMBED_LINKS);
        boolean hasChannelMessageWritePerms = selfMember.hasPermission(channel, Permission.MESSAGE_WRITE);
        boolean hasChannelSendEmbedLinksPerms = selfMember.hasPermission(channel, Permission.MESSAGE_EMBED_LINKS);

        if(!hasGuildMessageWritePerms) {
            messageToSend = "I do not have permission to write message in this guild. " +
                    "You can contact the guild administrators about this.";
        }else if(!hasGuildSendEmbedLinksPerms) {
            messageToSend = "I do not have permission to send embed links in this guild. " +
                    "You can contact the guild administrators about this.";
        } else if(!hasChannelSendEmbedLinksPerms) {
            messageToSend = "I do not have permission to send embed links in this Channel. " +
                    "You can contact the guild administrators about this.";
        } else if(!hasChannelMessageWritePerms) {
            messageToSend = "I do not have permission to write message in this channel. " +
                    "You can contact the guild administrators about this.";
        }

        if(hasChannelMessageWritePerms && hasGuildMessageWritePerms) {
            sendMessage(messageToSend, channel, 30);
        } else {
            sendEmbedPrivateMessage(messageToSend, MessageCategory.ERROR, event.getAuthor());
        }
    }
}