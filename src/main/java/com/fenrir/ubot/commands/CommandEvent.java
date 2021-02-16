package com.fenrir.ubot.commands;

import com.fenrir.ubot.config.Config;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.EnumSet;

public class CommandEvent {

    private final String prefix = Config.getConfig().getPrefix();
    private final MessageReceivedEvent event;
    private final MessageChannel channel;
    private String command;
    private String[] args;
    private final String AuthorId;

    public CommandEvent(MessageReceivedEvent event) {
        this.event = event;
        AuthorId = event.getAuthor().getId();
        channel = event.getChannel();
        prepareMessage();
    }

    private void prepareMessage() {

        String[] arr = Arrays.stream(event.getMessage().getContentRaw()
                .replaceFirst(prefix, "")
                .strip()
                .split(" (?![^{]*})"))    //arguments are split by " ". It ignores the content of the brackets.
                .map(String::trim)
                .toArray(String[]::new);

        command = arr[0].toLowerCase();

        try {
            args = new String[arr.length - 1];

            System.arraycopy(arr, 1, args, 0, arr.length - 1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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

    public Member getBotAsMember() {
        return event.getGuild().getSelfMember();
    }

    public EnumSet<Permission> getAuthorPermission() throws NullPointerException {
        return event.getMember().getPermissions();
    }

    public EnumSet<Permission> getBotPermission() {
        return event.getGuild().getSelfMember().getPermissions();
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public boolean isOwner() {
        return event.getMessage().getAuthor().getId().equals(event.getGuild().getOwnerId());
    }

}
