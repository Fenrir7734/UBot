package com.fenrir.ubot.listener;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandEvent;
import com.fenrir.ubot.commands.CommandList;
import com.fenrir.ubot.config.Config;
import com.fenrir.ubot.utilities.message.MessageCategory;
import com.fenrir.ubot.utilities.message.Messages;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class PrivateChannelListener extends ListenerAdapter {

    private final CommandList commandList = CommandList.getCommandList();

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.isFromType(ChannelType.PRIVATE)) {
            return;
        }

        if (!event.getMessage().getContentRaw().startsWith(Config.getConfig().getPrefix())) {
            return;
        }

        if (event.getAuthor().isBot()) {
            return;
        }

        CommandEvent commandEvent = new CommandEvent(event);
        try {
            Command command = commandList.search(commandEvent.getCommand());
            if(!command.isOnlyGuild()) {
                command.execute(commandEvent);
            } else {
                Messages.sendEmbedMessage("This command can be execute only within guild",
                        MessageCategory.WARNING,
                        event.getChannel());
            }
        } catch (NullPointerException e) {
            Messages.sendEmbedMessage("I can't find this command. To check list of commands type !help",
                    MessageCategory.ERROR,
                    event.getChannel());
        }
    }
}
