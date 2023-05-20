package org.example.command;


import org.example.collection.CollectionManager;
import org.example.collection.CollectionUtil;
import org.example.dtp.Request;
import org.example.dtp.Response;
import org.example.dtp.ResponseStatus;
import org.example.error.IllegalArgumentsException;
import org.example.error.NoSuchIDException;

import java.util.Scanner;

/**
 * Command remove_by_id id : remove an item from the collection by its id
 */
public class RemoveByIdCommand extends BaseCommand implements CollectionEditor{
    private final CollectionManager collectionManager;

    public RemoveByIdCommand(String name, String description, CollectionManager collectionManager) {
        super("remove_by_id", " id: удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;
    }
    /**
     * Исполнить команду
     * @param request аргументы команды
     * @throws IllegalArgumentsException неверные аргументы команды
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (request.getArgs().isBlank()) throw new IllegalArgumentsException();
        try {
            long ID = Long.parseLong(request.getArgs().trim());
            if (!CollectionUtil.checkExist(ID)) throw new NoSuchIDException();
            collectionManager.removeSpaceMarine(collectionManager.getByID(ID));
            return new Response(ResponseStatus.OK,"Объект удален успешно");
        } catch (NoSuchIDException err) {
            return new Response(ResponseStatus.ERROR,"В коллекции нет элемента с таким id");
        } catch (NumberFormatException exception) {
            return new Response(ResponseStatus.WRONG_ARGUMENTS,"id должно быть числом типа int");
        }
    }
}

