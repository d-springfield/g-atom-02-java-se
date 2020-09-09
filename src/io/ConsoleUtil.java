package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUtil {
    private final BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

    public Command readCommand() throws IOException {
        String pathPattern = "^.+(?= -[\\w]+($| \"))";
        String commandTypePattern = "(?<= -)[\\w]+(?=( \"|$))";
        String argumentPattern = "\".+\"$|$";

        boolean failed;
        String path, command, arguments;
        do {
            failed = false;
            String line = bf.readLine().trim();
            path = getStringByPattern(line, pathPattern, "path");
            command = getStringByPattern(line, commandTypePattern, "command type");
            arguments = getStringByPattern(line, argumentPattern, "arguments");
            if (arguments != null && arguments.length() > 1) {
                arguments = arguments.substring(1, arguments.length() - 1) + System.lineSeparator();
            }

            if (path == null || command == null || arguments == null) {
                failed = true;
            }
        } while (failed);

        return new Command(path, Enum.valueOf(CommandType.class, command.toUpperCase()), arguments);
    }

    public void printHelp() {
        printCommandFormat();
        printAvailableCommands();
    }

    private void printCommandFormat() {
        System.out.println("Commands format :");
        System.out.println("[path] -[command type] (\"arguments\")");
        System.out.println(System.lineSeparator());
    }

    private void printAvailableCommands() {
        System.out.println("Available commands : ");
        for (CommandType ct : CommandType.values()) {
            System.out.println(ct);
        }
        System.out.println(System.lineSeparator());
    }

    private String getStringByPattern(String input, String stringPattern, String msg) {
        Pattern pattern = Pattern.compile(stringPattern);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group();
        } else {
            System.out.println("Invalid " + msg + " pattern");
            return null;
        }
    }
}
