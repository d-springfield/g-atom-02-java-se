package io;

public class Command {
    private final String path;
    private final CommandType commandType;
    private final String argument;

    public Command(String path, CommandType commandType, String argument) {
        this.path = path;
        this.commandType = commandType;
        this.argument = argument;
    }

    public String getPath() {
        return path;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public String getArgument() {
        return argument;
    }
}
