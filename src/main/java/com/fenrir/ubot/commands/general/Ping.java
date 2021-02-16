package com.fenrir.ubot.commands.general;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandCategory;
import com.fenrir.ubot.commands.CommandEvent;
import com.fenrir.ubot.utilities.CommandVerifier;
import com.fenrir.ubot.utilities.PermissionsVerifier;
import net.dv8tion.jda.api.Permission;

import java.util.Collections;

public class Ping extends Command {

    public Ping() {
        super();
        category = CommandCategory.GENERAL;
        botRequiredPermissions = Collections.singletonList(Permission.MESSAGE_WRITE);
    }

    @Override
    public void execute(CommandEvent event) {

        if (!CommandVerifier.isCommandCorrect(event, minNumberOfArguments, maxNumberOfArguments, flags)
                || !PermissionsVerifier.checkPermissions(botRequiredPermissions, event)
                || isHelpFlag(event)) {
            return;
        }

        long currentTime = System.currentTimeMillis();

        event.getChannel().sendMessage("Pong")
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
