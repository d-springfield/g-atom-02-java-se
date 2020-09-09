package io;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileManager {
    private final String success = "Success";
    private final String notFoundFormat = "No such file/directory exists %s";
    private final String invalidPermission = "Invalid permission";

    public void execute(Command command) {
        File f = new File(command.getPath());

        switch (command.getCommandType()) {
            case READ:
                read(command);
                break;

            case WRITE:
                write(command);
                break;

            case CREATE:
                create(command);
                break;

            case REMOVE:
                remove(command);
                break;

            default:
                throw new IllegalArgumentException("Command type wasn't recognized : " + command.getCommandType());
        }
    }

    private void remove(Command command) {
        try {
            Files.deleteIfExists(Paths.get(command.getPath()));
            System.out.println(success);

        } catch (NoSuchFileException e) {
            System.out.println(String.format(notFoundFormat, Paths.get(command.getPath())));

        } catch (DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty.");

        } catch (IOException e) {
            System.out.println(invalidPermission);
        }
    }

    private void create(Command command) {
        Path path = Paths.get(command.getPath());
        if (Files.exists(path)) {
            System.out.println("Already exist");
            return;
        }

        Path parent = path.getParent();
        try {
            if (!Files.exists(parent)) {
                Files.createDirectories(parent);
            }

            Files.createFile(path);
            System.out.println(success);

        } catch (NoSuchFileException e) {
            System.out.println(String.format(notFoundFormat, path));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void write(Command command) {
        try {
            Files.write(Paths.get(command.getPath()), command.getArgument().getBytes(UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            System.out.println(success);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void read(Command command) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(command.getPath()));
            for (String line : lines) {
                System.out.println(line);
            }

        } catch (NoSuchFileException e) {
            System.out.println(String.format(notFoundFormat, command.getPath()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
