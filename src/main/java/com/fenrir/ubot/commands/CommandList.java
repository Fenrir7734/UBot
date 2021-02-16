package com.fenrir.ubot.commands;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandList {

    private static CommandList commandList = null;

    private final HashMap<String, Command> allCommands;
    private final HashMap<String, Command> initCommands;
    private final HashMap<CommandCategory, ArrayList<Command>> commandsByCategory;

    private CommandList() {
        allCommands = new HashMap<>();
        initCommands = new HashMap<>();
        commandsByCategory = new HashMap<>();
    }

    public static CommandList getCommandList() {
        if (commandList == null) {
            commandList = new CommandList();
        }
        return commandList;
    }

    public void addCommands(Command... commands) {
        for (Command value : commands) {
            allCommands.put(value.getCommand(), value);
            commandsByCategory.computeIfAbsent(value.getCategory(), k -> new ArrayList<>()).add(value);
        }
    }

    public void addInitCommands(Command... commands) {
        for (Command value : commands) {
            initCommands.put(value.getCommand(), value);
        }
        addCommands(commands);
    }

    public Command search(String command) {
        return allCommands.get(command);
    }

    public Command searchInit(String command) {
        return initCommands.get(command);
    }

    public HashMap<CommandCategory, ArrayList<Command>> getCommandsByCategory() {
        return commandsByCategory;
    }
}
