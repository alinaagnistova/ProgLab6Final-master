package org.example.command;

import org.example.client.RequestManager;
import org.example.common.network.CommandResult;
import org.example.common.network.Request;
import org.example.server.collection.CollectionManager;
/**
 * print_unique_melee_weapon :
 * print the unique values of the MeleeWeapon field of all items in the collection
 */
public class PrintUniqueMeleeWeaponCommand extends BaseCommand{
    public PrintUniqueMeleeWeaponCommand(RequestManager requestManager) {
        super(requestManager);
    }
    //    private final CollectionManager collection;

//    public PrintUniqueMeleeWeaponCommand(CollectionManager collection) {
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
//        }if (collection.getCollection().size() == 0){
//            System.out.println("Сказать нечего, файл пуст...");
//        } else {
//            collection.printUniqueMeleeWeapon();
//        }
    }


    public String getDescription() {
        return "print_unique_melee_weapon : вывести уникальные значения поля meleeWeapon всех элементов в коллекции";
    }

    @Override
    public String getName() {
        return "print_unique_melee_weapon";
    }
}
