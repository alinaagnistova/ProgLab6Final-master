package org.example.command;
import org.example.managers.CommandManager;
import org.example.server.collection.CollectionManager;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * execute_script file_name : read and execute the script from the specified file
 * The script contains commands in the same form as they are entered by the user in interactive mode.
 */
public class ExecuteScriptCommand extends BaseCommand {
    private final CollectionManager collection;
    private HashMap<String, BaseCommand> commandMap;
    private ArrayList<String> filePaths;
    static ArrayList<String> spaceMarineList = new ArrayList<>();
    static boolean flag = false;

    public ExecuteScriptCommand(CollectionManager collection) {
        this.collection = collection;
        this.commandMap = CommandManager.getCommandMap();
        this.filePaths = new ArrayList<>();
    }

    /**
     * performs a script reading with command processing
     * reads the file lines by adding the commands to the array
     * if the "add" command, creates an array with the persons characteristics
     *
     * @param args args -  command arguments
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            System.out.println("Вы неправильно ввели команду");
        } else {
            flag = true;
            filePaths.add((String) getArgument());
            ArrayList<String> commandList = new ArrayList<>();
            try (Scanner reader = new Scanner(new FileInputStream((String) getArgument()))) {

                while (reader.hasNextLine()) {
                    String line = reader.nextLine().trim();
                    commandList.add(line);
                }

            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден");
            }

            for (int i = 0; i < commandList.size(); i++) {
                while (commandList.get(i).contains("  "))
                    commandList.get(i).replaceAll("  ", " ");

                String[] commandAndArgument = commandList.get(i).split(" ");
                String argument;
                String commandArg = commandAndArgument[0];

                if (commandAndArgument.length == 1)
                    argument = null;
                else if (commandAndArgument.length == 2)
                    argument = commandAndArgument[1];
                else {
                    System.out.println("Введите комманду и аргумент, если нужно");
                    return;
                }
                String[] arrayList = new String[]{"add", "add_if_min", "add_if_max", "update"};
                boolean specialCommand = Arrays.asList(arrayList).contains(commandArg);
                try {
                    if (commandMap.containsKey(commandArg) && !specialCommand) {
                        if (commandArg.equals("execute_script")) {
                            if (filePaths.contains(commandAndArgument[1])) {
                                System.out.println("Файл содержит рекурсию!!");
                                continue;
                            }
                        }
                        commandMap.get(commandArg).setArgument(argument);
                        commandMap.get(commandArg).execute(commandAndArgument);

                    } else if (specialCommand) {
                        for (int j = 1; j < 11; j++) {
                            spaceMarineList.add(commandList.get(i + j));
                        }
                        commandMap.get(commandArg).execute(commandAndArgument);
                        spaceMarineList.clear();
                        i += 10;
                    }

                } catch (NullPointerException | IndexOutOfBoundsException e) {
                    System.out.println("Неверные данные в скрипте, персонаж не создан");
                } catch (JAXBException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            filePaths.remove(getArgument());
        }
    }

    public static boolean getFlag() {
        return flag;
    }

    public static ArrayList<String> getSpaceMarineList() {
        return spaceMarineList;
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getDescription() {
        return "execute_script: считать и исполнить скрипт из указанного файла";
    }
}


