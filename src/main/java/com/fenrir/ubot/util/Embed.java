package com.fenrir.ubot.util;

import com.fenrir.ubot.config.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class Embed {

    public static MessageEmbed commandHelpFormatting(String command, String briefDescription, String specificDescription) {
        return new EmbedBuilder().setTitle("`" + Config.getConfig().getPrefix() + command + "`")
                .setColor(Color.BLACK)
                .addField("Description", briefDescription, false)
                .addField("Specific Description", specificDescription, true)
                .build();
    }

}
