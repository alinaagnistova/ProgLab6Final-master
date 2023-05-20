package org.example.command;

import org.example.client.RequestManager;
import org.example.common.network.CommandResult;
import org.example.common.network.Request;
import org.example.server.collection.CollectionManager;
/**
 * sort : sort the collection in natural order
 */
public class SortCommand extends BaseCommand{
    public SortCommand(RequestManager requestManager) {
        super(requestManager);
    }
    //    private final CollectionManager collection;

//    public SortCommand(CollectionManager collection) {
//        this.collection = collection;
//    }

    public void execute(String[] args) {
        if (args.length > 1) {
            System.out.println("Вы неправильно ввели команду");
        } else {
            Request<String> request = new Request<>(getName(), null, null);
            CommandResult result = requestManager.sendRequest(request);
            if (result.status) {
                System.out.println((result.message));
            } else
                System.out.println("Ошибка");
        }
//        if (args.length > 1) {
//            System.out.println("Вы неправильно ввели команду");
//        } if (collection.getCollection().size() == 0){
//            System.out.println("Сортировать нечего, файл пуст...");
//        } else {
//            collection.sort();
//            System.out.println("Команда выполнена");
//        }
    }


    public String getDescription() {
        return "sort : отсортировать коллекцию в естественном порядке";
    }

    @Override
    public String getName() {
        return "sort";
    }
}
