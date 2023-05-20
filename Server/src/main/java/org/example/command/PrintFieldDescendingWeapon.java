package org.example.command;

import org.example.client.RequestManager;
import org.example.common.network.CommandResult;
import org.example.common.network.Request;
import org.example.server.collection.CollectionManager;
/**
 * print_field_descending_weapon_type :
 * print the values of the weaponType field of all elements in descending order
 */
public class PrintFieldDescendingWeapon extends BaseCommand{
    public PrintFieldDescendingWeapon(RequestManager requestManager) {
        super(requestManager);
    }
//    private final CollectionManager collection;

//    public PrintFieldDescendingWeapon(CollectionManager collection) {
//        this.collection = collection;
//    }

    public void execute(String[] args) {
        if (args.length != 1) {
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
//        }
//        if (collection.getCollection().size() == 0){
//            System.out.println("Сказать нечего, файл пуст...");
//        }else {
//            collection.printFieldDescendingWeapon();
//        }
    }


    public String getDescription() {
        return "print_field_descending_weapon_type : вывести значения поля weaponType всех элементов в порядке убывания";
    }

    @Override
    public String getName() {
        return "print_field_descending_weapon_type";
    }
}
