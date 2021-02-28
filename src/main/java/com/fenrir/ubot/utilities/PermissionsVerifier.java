package com.fenrir.ubot.utilities;

import com.fenrir.ubot.commands.CommandEvent;
import com.fenrir.ubot.utilities.message.CommandErrorsMsg;
import com.fenrir.ubot.utilities.message.MessageCategory;
import com.fenrir.ubot.utilities.message.Messages;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.util.Collection;

public class PermissionsVerifier {

    public static boolean checkPermissions(Collection<Permission> userRequiredPermissions, Collection<Permission> botRequiredPermissions, CommandEvent event) {
        if(event.getEvent().isFromType(ChannelType.PRIVATE)) {
            return false;
        }
        return checkPermissions(botRequiredPermissions, event) && checkUserPermission(userRequiredPermissions, event);
    }

    public static boolean checkPermissions(Collection<Permission> botRequiredPermissions, CommandEvent event) {
        return event.getEvent().isFromType(ChannelType.PRIVATE) || checkBotPermission(botRequiredPermissions, event);
    }

    public static boolean canBotSendMessages(CommandEvent event) {
        if(event.getEvent().isFromType(ChannelType.PRIVATE)) {
            return true;
        }

        Member member = event.getBotAsMember();
        TextChannel channel = (TextChannel) event.getChannel();

        return member.hasPermission(channel, Permission.MESSAGE_WRITE, Permission.MESSAGE_EMBED_LINKS);
    }

    public static boolean canBotSendMessages(Member member, MessageChannel channel) {
        if(channel instanceof TextChannel) {
            return member.hasPermission((TextChannel) channel, Permission.MESSAGE_WRITE);
        }
        return channel instanceof PrivateChannel;
    }

    public static boolean checkBotPermission(Collection<Permission> requiredPermissions, CommandEvent event) {
        Member member = event.getBotAsMember();
        TextChannel channel = (TextChannel) event.getChannel();

        if(!member.hasPermission(channel, requiredPermissions)) {
            if(canBotSendMessages(event)) {
                Messages.sendEmbedMessage(CommandErrorsMsg.BOT_TOO_LOW_PERMISSION, MessageCategory.ERROR, channel);
            }
            return false;
        }
        return true;
    }

    private static boolean checkUserPermission(Collection<Permission> requiredPermissions, CommandEvent event) {
        Member member = event.getAuthorAsMember();
        TextChannel channel = (TextChannel) event.getChannel();

        if(!member.hasPermission(channel, requiredPermissions)) {
            Messages.sendEmbedMessage(CommandErrorsMsg.USER_TOO_LOW_PERMISSION, MessageCategory.ERROR, channel);
            return false;
        }
        return true;
    }


    public static boolean checkRoleHierarchy(CommandEvent event, Member target) {
        if(event.isOwner()) {
            return true;
        }

        if(!event.getBotAsMember().canInteract(target)) {
            return false;
        }

        return event.getAuthorAsMember().canInteract(target);
    }

}
