package com.fenrir.ubot.utilities;

import com.fenrir.ubot.commands.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Collection;

public class PermissionsVerifier {

    public static boolean checkPermissions(Collection<Permission> userRequiredPermissions, Collection<Permission> botRequiredPermissions, CommandEvent event) {
        return checkPermissions(botRequiredPermissions, event) && checkUserPermission(userRequiredPermissions, event);
    }

    public static boolean checkPermissions(Collection<Permission> botRequiredPermissions, CommandEvent event) {

        if(!isFromGuild(event)) {
            return false;
        }

        return checkBotPermission(botRequiredPermissions, event);
    }
    //Czy tutaj też powinienem sprawdzać czy MessageChannel jest instancją TextChannel?
    public static boolean canBotSendMessages(CommandEvent event) {
        Member member = event.getBotAsMember();
        TextChannel channel = (TextChannel) event.getChannel();

        return member.hasPermission(channel, Permission.MESSAGE_WRITE);
    }

    public static boolean canBotSendMessages(Member member, MessageChannel channel) {
        if(channel instanceof TextChannel) {
            return member.hasPermission((TextChannel) channel, Permission.MESSAGE_WRITE);
        }
        return false;
    }

        //someday that may be expanded
    private static boolean isFromGuild(CommandEvent event) {
        return event.getEvent().isFromGuild();
    }
    //Trzeba tu sprawdzać czy channel jest TextChannel czy mozę Private Channel
    public static boolean checkBotPermission(Collection<Permission> requiredPermissions, CommandEvent event) {
        Member member = event.getBotAsMember();
        TextChannel channel = (TextChannel) event.getChannel();

        if(!member.hasPermission(channel, requiredPermissions)) {
            if(canBotSendMessages(event)) {
                Messages.sendBasicEmbedMessage(CommandErrorsMsg.BOT_TOO_LOW_PERMISSION, MessageCategory.ERROR, channel);
            }
            return false;
        }
        return true;
    }

    private static boolean checkUserPermission(Collection<Permission> requiredPermissions, CommandEvent event) {
        Member member = event.getAuthorAsMember();
        TextChannel channel = (TextChannel) event.getChannel();

        if(!member.hasPermission(channel, requiredPermissions)) {
            Messages.sendBasicEmbedMessage(CommandErrorsMsg.USER_TOO_LOW_PERMISSION, MessageCategory.ERROR, channel);
            return false;
        }
        return true;
    }

}
