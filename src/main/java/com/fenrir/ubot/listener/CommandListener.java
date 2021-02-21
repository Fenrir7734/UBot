package com.fenrir.ubot.listener;

import com.fenrir.ubot.commands.CommandEvent;
import com.fenrir.ubot.commands.CommandList;
import com.fenrir.ubot.config.Config;
import com.fenrir.ubot.config.GuildSettings;
import com.fenrir.ubot.utilities.MessageCategory;
import com.fenrir.ubot.utilities.Messages;
import com.fenrir.ubot.utilities.PermissionsVerifier;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class CommandListener extends ListenerAdapter {

    private final CommandList commandList = CommandList.getCommandList();

    //Może przydało by sie to zmienić na onGuildMessageReceived
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.PRIVATE)) {
            return;
        }

        if (event.getAuthor().isBot()) {
            return;
        }

        String prefix;
        GuildSettings guildSettings = GuildSettings.getSettings(event.getGuild());

        if (guildSettings == null) {
            guildSettings = new GuildSettings(event.getGuild());
            GuildSettings.addGuildSettings(guildSettings);
        }
        prefix = guildSettings.getPrefix();

        if (!event.getMessage().getContentRaw().startsWith(prefix)) {
            return;
        }

        boolean hasSendingPermission = PermissionsVerifier.canBotSendMessages(event.getGuild().getSelfMember(), event.getChannel());

        if (event.getMessage().getContentRaw().length() <= 1) {
            if(hasSendingPermission) {
                Messages.sendBasicEmbedMessage("You have to specify the command. To check list of commands type !help",
                        MessageCategory.WARNING,
                        event.getChannel());
            }
            return;
        }

        CommandEvent commandEvent = new CommandEvent(event);

        try {
            if (Config.getConfig().isActive()) {
                commandList.search(commandEvent.getCommand()).execute(commandEvent);
            } else {
                commandList.searchInit(commandEvent.getCommand()).execute(commandEvent);
            }
        } catch (NullPointerException e) {
            if(hasSendingPermission) {
                Messages.sendBasicEmbedMessage("I can't find this command. To check list of commands type !help",
                        MessageCategory.ERROR,
                        event.getChannel());
            }
        }

    }
}
