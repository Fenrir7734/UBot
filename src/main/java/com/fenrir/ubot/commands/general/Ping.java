package com.fenrir.ubot.commands.general;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandCategory;
import com.fenrir.ubot.commands.CommandEvent;
import com.fenrir.ubot.util.CommandErrorsMsg;
import net.dv8tion.jda.api.entities.MessageChannel;

public class Ping extends Command {

    public Ping() {
        super();
        category = CommandCategory.GENERAL;
    }

    @Override
    public void execute(CommandEvent event) {

        if(!isCommandCorrect(event) || isHelpFlag(event)) {
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
