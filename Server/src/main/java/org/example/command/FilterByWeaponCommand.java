package org.example.command;

import org.example.collection.CollectionManager;
import org.example.data.Weapon;
import org.example.dtp.Request;
import org.example.dtp.Response;
import org.example.dtp.ResponseStatus;
import org.example.error.IllegalArgumentsException;


import java.util.Objects;

/**
 * filter_by_weapon_type weaponType :
 * output elements whose value of the weaponType field is equal to the specified
 */
public class FilterByWeaponCommand extends BaseCommand{
    private CollectionManager collectionManager;

    public FilterByWeaponCommand(CollectionManager collectionManager) {
        super("filter_by_weapon_type", "вывести элементы, значение поля weaponType которых равно заданному");
        this.collectionManager = collectionManager;
    }

    /**
     * Исполнить команду
     *
     * @param request аргументы команды
     * @throws IllegalArgumentsException неверные аргументы команды
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        if (Objects.isNull(request.getObject())){
            return new Response(ResponseStatus.ASK_OBJECT, "Для команды " + this.getName() + " требуется объект");
        }
        if (CollectionManager.getCollection() == null || CollectionManager.getCollection().isEmpty()) {
            return new Response(ResponseStatus.ERROR, "Коллекция еще не инициализирована");
        }
        Weapon weapon = Weapon.valueOf(request.getArgs().trim());
        return new Response(ResponseStatus.OK, "Коллекция: " + collectionManager.filterByWeapon(weapon));
    }

}
