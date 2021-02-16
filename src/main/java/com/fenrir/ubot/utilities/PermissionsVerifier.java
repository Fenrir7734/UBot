package com.fenrir.ubot.utilities;

import com.fenrir.ubot.commands.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
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

    private static boolean canBotSendMessages(CommandEvent event) {
        Member member = event.getBotAsMember();
        TextChannel channel = (TextChannel) event.getChannel();

        return member.hasPermission(channel, Permission.MESSAGE_WRITE);
    }

        //someday that may be expanded
    private static boolean isFromGuild(CommandEvent event) {
        return event.getEvent().isFromGuild();
    }

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
