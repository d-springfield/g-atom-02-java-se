package io;

import java.io.IOException;

public class Demo {
    public static void main(String[] args) {
        FileManager fileManager = new FileManager();
        ConsoleUtil consoleUtil = new ConsoleUtil();

        run(fileManager, consoleUtil);
    }

    private static void run(FileManager fileManager, ConsoleUtil consoleUtil) {
        consoleUtil.printHelp();
        while (true){
            try {
                Command command = consoleUtil.readCommand();
                fileManager.execute(command);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
