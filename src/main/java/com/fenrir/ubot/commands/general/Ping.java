package com.fenrir.ubot.commands.general;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandEvent;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Date;

public class Ping extends Command {

    @Override
    public void execute(CommandEvent event) {
        MessageChannel channel = event.getChannel();

        long currentTime = System.currentTimeMillis();

        channel.sendMessage("Pong")
                .queue(time ->
                        time.editMessage("Pong " + (System.currentTimeMillis() - currentTime) + "ms")
                        .queue());
    }

    @Override
    public String getCommand() {
        return "ping";
    }

    @Override
    public String getBriefDescription() {
        return "Checks if the bot is active";
    }

    @Override
    public String getSpecificDescription() {
        return null;
    }
}
