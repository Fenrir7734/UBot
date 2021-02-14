package com.fenrir.ubot.listener;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandEvent;
import com.fenrir.ubot.commands.CommandList;
import com.fenrir.ubot.config.Config;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class CommandListener extends ListenerAdapter {

    private final String prefix = Config.getConfig().getPrefix();
    private final CommandList commandList = CommandList.getCommandList();

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if(event.getAuthor().isBot()) {
            return;
        }

        if(!event.getMessage().getContentRaw().startsWith(prefix)) {
            return;
        }

        if(event.getMessage().getContentRaw().length() <= 1) {
            event.getChannel()
                    .sendMessage("You have to specify the command. To check list of commands type !help")
                    .queue();
            return;
        }

        CommandEvent commandEvent = new CommandEvent(event);

        try {
            if(Config.getConfig().isActive()) {
                commandList.search(commandEvent.getCommand()).execute(commandEvent);
            } else {
                commandList.searchInit(commandEvent.getCommand()).execute(commandEvent);
            }
        } catch (NullPointerException e) {
            event.getChannel()
                    .sendMessage("I can't find this command. To check list of commands type !help")
                    .queue();
        }

    }

}
