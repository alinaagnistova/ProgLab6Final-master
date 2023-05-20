package org.example.command;

import org.example.client.RequestManager;
import org.example.common.network.CommandResult;
import org.example.common.network.Request;
import org.example.server.collection.CollectionManager;
/**
 * shuffle : shuffle collection items in random order
 */
public class ShuffleCommand extends BaseCommand{
    public ShuffleCommand(RequestManager requestManager) {
        super(requestManager);
    }
    //    private final CollectionManager collection;

//    public ShuffleCommand(CollectionManager collection) {
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
//            collection.shuffle();
//            System.out.println("Команда выполнена");
//        }
    }


    public String getDescription() {
        return "shuffle : перемешать элементы коллекции в случайном порядке";
    }

    @Override
    public String getName() {
        return "shuffle";
    }
}
