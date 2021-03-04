package com.fenrir.ubot.listener;

import com.fenrir.ubot.config.GuildSettings;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class GuildReadyListener extends ListenerAdapter {

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        GuildSettings.addGuildSettings(new GuildSettings(event.getGuild()));

        for(TextChannel channel: event.getGuild().getTextChannels()) {
            if(channel.canTalk()) {
                String message = "Hi!\n My name is u Ubot and I am bot. " +
                        "\n If you want to know what commands you can use, type <prefix> help\n";
                //Messages.sendEmbedMessage(message, MessageCategory.MESSAGE, channel);
            }
        }
    }
}
