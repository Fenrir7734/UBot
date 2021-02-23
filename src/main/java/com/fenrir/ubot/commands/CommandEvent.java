package com.fenrir.ubot.commands;

import com.fenrir.ubot.config.Config;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class CommandEvent {

    private final String prefix = Config.getConfig().getPrefix();
    private final MessageReceivedEvent event;
    private final MessageChannel channel;
    private Message message;
    private String command;
    private String[] args;
    private String[] flags;
    private final String AuthorId;

    public CommandEvent(MessageReceivedEvent event) {
        this.event = event;
        AuthorId = event.getAuthor().getId();
        channel = event.getChannel();
        message = event.getMessage();
        prepareMessage();
    }

    private void prepareMessage() {

        List<String> list = Arrays.stream(event.getMessage().getContentRaw()
                .replaceFirst(prefix, "")
                .strip()
                .split(" (?![^{]*})"))    //arguments are split by " ". It ignores the content of the brackets.
                .map(String::trim)
                .collect(Collectors.toList());

        command = list.get(0).toLowerCase();
        flags = list.stream()
                .filter(argument -> argument.startsWith("-"))
                .toArray(String[]::new);

        list.remove(command);
        if(flags.length > 0) {
            list.removeAll(Arrays.asList(flags.clone()));
        }

        args = list.toArray(String[]::new);
    }

    public MessageReceivedEvent getEvent() {
        return event;
    }

    public String getAuthorId() {
        return AuthorId;
    }

    public Member getAuthorAsMember() {
        return event.getMember();
    }

    public User getAuthorAsUser() {
        return event.getAuthor();
    }

    public Member getBotAsMember() {
        return event.getGuild().getSelfMember();
    }

    public EnumSet<Permission> getAuthorPermission() throws NullPointerException {
        return event.getMember().getPermissions();
    }

    public EnumSet<Permission> getBotPermission() {
        return event.getGuild().getSelfMember().getPermissions();
    }

    public Message getMessage() {
        return message;
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

    public String[] getFlags() {
        return flags;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public TextChannel getFirstTextChannel() {
        if(!event.isFromType(ChannelType.PRIVATE)) {
            for(TextChannel channel: event.getGuild().getTextChannels()) {
                if(channel.canTalk()) {
                    return channel;
                }
            }
        }
        return null;
    }

    public boolean hasAuthorPrivateChannel() {
        return getAuthorAsUser().hasPrivateChannel();
    }

    public boolean isOwner() {
        return event.getMessage().getAuthor().getId().equals(event.getGuild().getOwnerId());
    }

}
