package org.example.managers;

import org.example.command.BaseCommand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.command.CollectionEditor;
import org.example.dtp.Request;
import org.example.dtp.Response;
import org.example.error.CommandRuntimeException;
import org.example.error.ExitObligedException;
import org.example.error.IllegalArgumentsException;
import org.example.error.NoSuchCommandException;

/**
 * Командный менеджер.
 * Реализует паттерн программирования Command
 */
public class CommandManager{
    /**
     * Поле для хранения комманд в виде Имя-Комманда
     */
    private final HashMap<String, BaseCommand> commands = new HashMap<>();
    /**
     * Поле для истории команд
     */
    private final FileManager fileManager;

    static final Logger commandManagerLogger = LogManager.getLogger(CommandManager.class);

    public CommandManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void addCommand(BaseCommand command){
        this.commands.put(command.getName(), command);
        commandManagerLogger.info("Добавлена команда", command);
    }
    public void addCommand(Collection<BaseCommand> commands){
        this.commands.putAll(commands.stream()
                .collect(Collectors.toMap(BaseCommand::getName, s -> s)));
        commandManagerLogger.info("Добавлены комманды", commands);
    }
    public Collection<BaseCommand> getCommands(){
        return commands.values();
    }



    /**
     * Выполняет команду
     * @param request - запрос клиента
     * @throws NoSuchCommandException такая команда отсутствует
     * @throws IllegalArgumentsException неверные аргументы команды
     * @throws CommandRuntimeException команда выдала ошибку при исполнении
     * @throws ExitObligedException команда вызвала выход из программы
     */
    public Response execute(Request request) throws NoSuchCommandException, IllegalArgumentsException, CommandRuntimeException, ExitObligedException {
        BaseCommand command = commands.get(request.getCommandName());
        if (command == null) {
            commandManagerLogger.fatal("Нет такой команды" + request.getCommandName());
            throw new NoSuchCommandException();
        }
        Response response = command.execute(request);
        commandManagerLogger.info("Выполнение команды\n", response);
        if (command instanceof CollectionEditor) {
            commandManagerLogger.info("Файл обновлен");
            FileManager.saveToXml();
        }
        return response;
    }
}