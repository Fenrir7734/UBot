package com.fenrir.ubot.util;

import com.fenrir.ubot.commands.Command;
import com.fenrir.ubot.commands.CommandCategory;
import com.fenrir.ubot.config.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Embed {

    public static MessageEmbed commandHelpFormatting(String command, String briefDescription, String specificDescription) {
        return new EmbedBuilder().setTitle("`" + Config.getConfig().getPrefix() + command + "`")
                .setColor(Color.BLACK)
                .addField("Description", briefDescription, false)
                .addField("Specific Description", specificDescription, true)
                .build();
    }
    
    public static MessageEmbed basicHelpFormatting(HashMap<CommandCategory, ArrayList<Command>> commands) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("**Help**");

        for(CommandCategory category: commands.keySet()) {
            eb.addField(category.getValue(), "", false);
            for(Command command: commands.get(category)) {
                eb.addField("`" + command.getCommand() + "`", command.getBriefDescription(), true);
            }
        }
        
        return eb.build();
    }

    public static MessageEmbed basicHelpFormatting2(HashMap<CommandCategory, ArrayList<Command>> commands) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("**Help**");

        for(CommandCategory category: commands.keySet()) {
            StringBuilder stringBuilder = new StringBuilder();
            for(Command command: commands.get(category)) {
                stringBuilder.append("`").append(Config.getConfig().getPrefix()).append(command.getCommand()).append("` ").append(command.getBriefDescription()).append("\n");
            }
            eb.addField(category.getValue(), stringBuilder.toString(), false);
        }

        return eb.build();
    }

    public static MessageEmbed listHelpFormatting(HashMap<CommandCategory, ArrayList<Command>> commands) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("**Command List**");

        for(CommandCategory category: commands.keySet()) {
            StringBuilder stringBuilder = new StringBuilder();
            for(Command command: commands.get(category)) {
                stringBuilder.append("`").append(Config.getConfig().getPrefix()).append(command.getCommand()).append("` ");
            }
            eb.addField(category.getValue(), stringBuilder.toString(), false);
        }

        return eb.build();
    }

}
