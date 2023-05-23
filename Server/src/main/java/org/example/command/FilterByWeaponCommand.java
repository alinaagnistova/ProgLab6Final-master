package org.example.command;

import org.example.collection.CollectionManager;
import org.example.collection.CollectionUtil;
import org.example.data.SpaceMarine;
import org.example.data.Weapon;
import org.example.dtp.Request;
import org.example.dtp.Response;
import org.example.dtp.ResponseStatus;
import org.example.error.IllegalArgumentsException;


import java.util.ArrayList;
import java.util.Objects;

/**
 * filter_by_weapon_type weaponType :
 * output elements whose value of the weaponType field is equal to the specified
 */
public class FilterByWeaponCommand extends BaseCommand{
    private CollectionManager collectionManager;

    public FilterByWeaponCommand(CollectionManager collectionManager) {
        super("filter_by_weapon_type", "filter_by_weapon_type weaponType: вывести элементы, значение поля weaponType которых равно заданному");
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
        Weapon weapon;
        if (request.getArgs().isBlank()) throw new IllegalArgumentsException();
         weapon = Weapon.valueOf(request.getArgs().trim().toUpperCase());
        return new Response(ResponseStatus.OK, "Элементы коллекции с заданным weaponType: \n" + collectionManager.filterByWeapon(weapon));
    }

}
