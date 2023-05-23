package org.example.command;


import org.example.collection.CollectionManager;
import org.example.dtp.Request;
import org.example.dtp.Response;
import org.example.dtp.ResponseStatus;
import org.example.error.CommandRuntimeException;
import org.example.error.ExitObligedException;
import org.example.error.IllegalArgumentsException;

/**
 * Command clear : clear the collection
 */
public class ClearCommand extends BaseCommand implements CollectionEditor{
    private CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "clear: очистить коллекцию");
        this.collectionManager = collectionManager;
    }


    @Override
    public Response execute(Request request) throws CommandRuntimeException, ExitObligedException, IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        collectionManager.clear();
        return new Response(ResponseStatus.OK,"Элементы удалены\n");
    }
}
