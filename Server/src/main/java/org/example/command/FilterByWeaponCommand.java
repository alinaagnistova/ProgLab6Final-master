package org.example.command;

import org.example.client.RequestManager;
import org.example.common.network.CommandResult;
import org.example.common.network.Request;
import org.example.server.collection.CollectionManager;
import org.example.common.data.Weapon;
/**
 * filter_by_weapon_type weaponType :
 * output elements whose value of the weaponType field is equal to the specified
 */
public class FilterByWeaponCommand extends BaseCommand{
    public FilterByWeaponCommand(RequestManager requestManager) {
        super(requestManager);
    }
    //    private final CollectionManager collection;

//    public FilterByWeaponCommand(CollectionManager collection) {
//        this.collection = collection;
//    }

    public void execute(String[] args) {
        if (args.length != 2) {
            System.out.println("Вы неправильно ввели команду");
        } else {
            Request<String> request = new Request<>(getName(), args[1], null);
            CommandResult result = requestManager.sendRequest(request);
            if (result.status) {
                System.out.println((result.message));
            } else
                System.out.println("Ошибка");
        }
    }
//        Weapon weapon;
//        if (args.length != 2) {
//            System.out.println("Вы неправильно ввели команду");
//        } if (collection.getCollection().size() == 0){
//            System.out.println("Фильтровать нечего, файл пуст...");
//        }
//        else {
//            try {
//                weapon = Weapon.valueOf(args[1].toUpperCase());
//                collection.filterByWeapon(weapon);
//                System.out.println("Команда выполнена");
//            }catch (IllegalArgumentException e){
//                System.out.println("Тип оружия введен неверно, попробуйте снова:(");
//            }
//        }



    public String getDescription() {
        return "filter_by_weapon_type weaponType : вывести элементы, значение поля weaponType которых равно заданному";
    }

    @Override
    public String getName() {
        return "filter_by_weapon_type";
    }
}
