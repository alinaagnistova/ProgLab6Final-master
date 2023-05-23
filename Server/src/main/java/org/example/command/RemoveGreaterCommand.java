package org.example.command;


import org.example.collection.CollectionManager;
import org.example.data.SpaceMarine;
import org.example.dtp.*;
import org.example.error.FileModeException;
import org.example.error.IllegalArgumentsException;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;


/**
 * Command remove_greater {element} : remove all elements from the collection that exceed the specified
 */
public class RemoveGreaterCommand extends BaseCommand implements CollectionEditor{
    private final CollectionManager collectionManager;

    public RemoveGreaterCommand(CollectionManager collectionManager) {
        super("remove_greater", "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный");
        this.collectionManager = collectionManager;
    }

    /**
     * Исполнить команду
     * @param request аргументы команды
     * @throws  IllegalArgumentsException неверные аргументы команды
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (request.getArgs().isBlank()) throw new IllegalArgumentsException();
        try {
                float health = Float.parseFloat(request.getArgs().trim());
                if (health > 0) {
                    collectionManager.removeGreater(health);
                    return new Response(ResponseStatus.OK, "Удалены элементы большие чем заданный");
                } else {
                    System.out.println("Уровень здоровье должен быть больше 0");
                    return new Response(ResponseStatus.WRONG_ARGUMENTS, "Упс, что-то пошло не так");
                }
        } catch (NoSuchElementException e) {
            return new Response(ResponseStatus.ERROR, "В коллекции нет элементов");
        } catch (FileModeException e) {
            return new Response(ResponseStatus.ERROR, "Поля в файле не валидны! Объект не создан");
        } catch (NumberFormatException e){
            return new Response(ResponseStatus.ERROR, "Число введено неверно");
        }
    }
    }


