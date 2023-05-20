package org.example.command;


import org.example.collection.CollectionManager;
import org.example.dtp.Request;
import org.example.dtp.Response;
import org.example.dtp.ResponseStatus;
import org.example.error.IllegalArgumentsException;

import java.util.Objects;

/**
 * Command add {element}
 */
public class AddCommand extends BaseCommand implements CollectionEditor{
    private final CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        super("add", "add {element}: добавить новый элемент в коллекцию\"");
        this.collectionManager = collectionManager;
    }

    /**
     * add a new element to the collection
     *
     * @param request
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        if (Objects.isNull(request.getObject())){
            return new Response(ResponseStatus.ASK_OBJECT, "Для команды " + this.getName() + " требуется объект");
        } else{
            collectionManager.addSpaceMarine(request.getObject());
            return new Response(ResponseStatus.OK, "Объект успешно добавлен");
        }
    }

}