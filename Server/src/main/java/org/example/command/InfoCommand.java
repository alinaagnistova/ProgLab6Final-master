package org.example.command;


import org.example.collection.CollectionManager;
import org.example.dtp.Request;
import org.example.dtp.Response;
import org.example.dtp.ResponseStatus;
import org.example.error.IllegalArgumentsException;
import org.example.utils.ConsoleColors;

/**
 * info :
 * output to the standard output stream information about the collection
 * (type, initialization date, number of items, etc.)
 */
public class InfoCommand extends BaseCommand {
    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        super("info", ": вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        this.collectionManager = collectionManager;
    }
    /**
     * Исполнить команду
     * @param request аргументы команды
     * @throws IllegalArgumentsException неверные аргументы команды
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
//        String lastInitTime = (collectionManager.getLastInitTime() == null)
//                ? "В сессии коллекция не инициализирована"
//                : collectionManager.getLastInitTime().toString();
//        String lastSaveTime = (collectionManager.getLastSaveTime() == null)
//                ? "В сессии коллекция не инициализирована "
//                : collectionManager.getLastSaveTime().toString();
//        String stringBuilder = "Сведения о коллекции: \n" +
//                ConsoleColors.toColor("Тип: ", ConsoleColors.GREEN) + collectionManager.getClass().getSimpleName() + "\n" +
//                ConsoleColors.toColor("Количество элементов: ", ConsoleColors.GREEN) + collectionManager.getC + "\n" +
//                ConsoleColors.toColor("Дата последней инициализации: ", ConsoleColors.GREEN) + lastInitTime + "\n" +
//                ConsoleColors.toColor("Дата последнего изменения: ", ConsoleColors.GREEN) + lastSaveTime + "\n";
        return new Response(ResponseStatus.OK, collectionManager.info());
    }
}