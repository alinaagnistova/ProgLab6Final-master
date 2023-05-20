package org.example.server.collection;

import org.example.client.RequestManager;
import org.example.common.DataManager;
import org.example.common.data.LocalDateAdapter;
import org.example.common.data.MeleeWeapon;
import org.example.common.data.SpaceMarine;
import org.example.common.data.Weapon;
import org.example.common.error.IncorrectCollectionException;
import org.example.common.network.CommandResult;
import org.example.common.network.Request;
import org.example.client.command.CommandManager;


import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The class that implements collection related methods
 */
@XmlRootElement
@XmlSeeAlso({SpaceMarine.class})
@XmlAccessorType(XmlAccessType.FIELD)
public class CollectionManager extends DataManager implements ICollectionManager, Serializable {
    CollectionUtil collectionUtil = new CollectionUtil();
    private String filename = "s";

    @XmlElement
    private static LinkedList<SpaceMarine> collection = new LinkedList<>();
    @XmlTransient
    private LocalDate date = LocalDate.now();

    public CollectionManager(LinkedList<SpaceMarine> collection, String filename) {
        this.collection = collection;
        this.filename = filename;
    }

    public CollectionManager() {
    }

    /**
     * @return
     */
    public static LinkedList<SpaceMarine> getCollection() {
        Collections.sort(collection);
        return collection;
    }

    @XmlElement(name = "creation_date_collectionManager")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * upload the collection to the server
     *
     * @param request - collection
     */
    public void loadCollection(LinkedList<SpaceMarine> request) throws JAXBException {
        setCollection(request);
    }

    public void setCollection(LinkedList<SpaceMarine> spaceMarineCollection) {
        for (SpaceMarine spaceMarine : spaceMarineCollection) {
            spaceMarine.setName(spaceMarine.getName());
            spaceMarine.setCoordinates(spaceMarine.getCoordinates());
            spaceMarine.setHealth(spaceMarine.getHealth());
            spaceMarine.setChapter(spaceMarine.getChapter());
            spaceMarine.setCategory(spaceMarine.getCategory());
            spaceMarine.setWeaponType(spaceMarine.getWeaponType());
            spaceMarine.setMeleeWeapon(spaceMarine.getMeleeWeapon());
        }
        this.collection = spaceMarineCollection;
    }
    /**
     * method for update find the character with the entered id
     *
     * @param ID - id that the client entered
     * @return person
     */
    public SpaceMarine getByID(Long ID) {
        return collection.stream()
                .filter(spaceMarine -> spaceMarine.getId() == ID)
                .findFirst()
                .orElse(null);
    }

//    /**
//     * print info about collection
//     */
//    @Override
//    public void info() {
//        String info = "Type of collection:" + collection.getClass().getSimpleName() + "\nDate of initialization:" + date + "\nNumbers of elements:" + collection.size();
//        System.out.println(info);
//    }

    /**
     * print info about each element in collection
     */
    @Override
    public String information() {
        if (collection.isEmpty()) {
            System.out.println("В коллекции нет объектов, доступных для просмотра");
        }
        StringBuilder information = new StringBuilder();
        for (SpaceMarine spaceMarine : collection) {
            information.append(CollectionUtil.display(spaceMarine));
        }
        return information.toString();
    }

    /**
     * adds SpaceMarine
     *
     * @param spaceMarine
     */
    @Override
    public void addSpaceMarine(SpaceMarine spaceMarine) {
        collection.add(spaceMarine);

    }

//    /**
//     * updates data of spaceMarine, ID stays the same
//     *
//     * @param newSpaceMarine
//     * @param ID
//     */
//    @Override
//    public void updateId(SpaceMarine newSpaceMarine, Long ID) {
//        for (SpaceMarine spaceMarine : collection) {
//            if (spaceMarine.getId().equals(ID)) {
//                spaceMarine.setName(newSpaceMarine.getName());
//                spaceMarine.setCoordinates(newSpaceMarine.getCoordinates());
//                spaceMarine.setHealth(newSpaceMarine.getHealth());
//                spaceMarine.setChapter(newSpaceMarine.getChapter());
//                if (spaceMarine.getCategory() != null) {
//                    spaceMarine.setCategory(newSpaceMarine.getCategory());
//                }
//                if (spaceMarine.getWeaponType() != null) {
//                    spaceMarine.setWeaponType(newSpaceMarine.getWeaponType());
//                }
//                if (spaceMarine.getMeleeWeapon() != null) {
//                    spaceMarine.setMeleeWeapon(newSpaceMarine.getMeleeWeapon());
//                }
//            }
//        }
//    }
//
//    /**
//     * remove SpaceMarine by id
//     *
//     * @param ID
//     */

//    @Override
//    public void removeById(Long ID) {
//        Iterator<SpaceMarine> iterator = collection.iterator();
//        while (iterator.hasNext()) {
//            SpaceMarine spaceMarine = iterator.next();
//            {
//                if (spaceMarine.getId().equals(ID)) {
//                    iterator.remove();
//                    System.out.println("Элемент удален из коллекции");
//                } else if (!iterator.hasNext()) {
//                    System.out.println("Элемента с таким ID не существует");
//                }
//            }
//        }
//    }
//    /**
//     * clear collection
//     *
//     */
//    @Override
//    public void clear() {
//        collection.clear();
//    }

//    /**
//     * shuffle collection
//     */
//    @Override
//    public void shuffle() {
//        Collections.shuffle(collection);
//    }

//    /**
//     * removes the healthiest person
//     *
//     * @param health
//     */
//    @Override
//    public void removeGreater(Float health) {
//        Iterator<SpaceMarine> iterator = collection.iterator();
//        while (iterator.hasNext()) {
//            SpaceMarine spaceMarine = iterator.next();
//            {
//                if (spaceMarine.getHealth() > health) {
//                    iterator.remove();
//                    System.out.println("Элемент удален из коллекции: " + spaceMarine.getName());
//                } else if (!iterator.hasNext()) {
//                    System.out.println("Нет элементов с таким же уровнем здоровья");
//                }
//            }
//        }
//    }

//    /**
//     * sort collection
//     */
//    @Override
//    public void sort() {
//        Collections.sort(collection);
//    }

//    /**
//     * show elements with this kind of weapon
//     *
//     * @param weaponType
//     */
//    @Override
//    public void filterByWeapon(Weapon weaponType) {
//        ArrayList<SpaceMarine> filterObjects = new ArrayList<>();
//        for (SpaceMarine spaceMarine : collection) {
//            if (spaceMarine.getWeaponType() == weaponType) {
//                filterObjects.add(spaceMarine);
//            }
//        }
//        if (filterObjects.size() == 0) {
//            System.out.println("Нет ни одного экземпляра с таким полем");
//        } else {
//            filterObjects.forEach(CollectionUtil::display);
//        }
//    }

//    /**
//     * find unique meleeWeapons and print them
//     */
//    @Override
//    public void printUniqueMeleeWeapon() {
//        ArrayList<MeleeWeapon> uniq = new ArrayList<>();
//        for (SpaceMarine spaceMarine : collection) {
//            MeleeWeapon meleeWeapon = spaceMarine.getMeleeWeapon();
//            if (Collections.frequency(uniq, meleeWeapon) == 0) {
//                uniq.add(meleeWeapon);
//            }
//        }
//        System.out.println(uniq);
//    }

//    /**
//     * find all weapons in collection and print it (descending)
//     */
//    @Override
//    public void printFieldDescendingWeapon() {
//        ArrayList<Weapon> descendingWeapon = new ArrayList<>();
//        for (SpaceMarine spaceMarine : collection) {
//            Weapon weaponType = spaceMarine.getWeaponType();
//            if (Collections.frequency(descendingWeapon, weaponType) == 0) {
//                descendingWeapon.add(weaponType);
//            }
//        }
//        Collections.sort(descendingWeapon, Collections.reverseOrder());
//        System.out.println(descendingWeapon);
//    }
@Override
    public void checkCollection() {
        for (SpaceMarine spaceMarine : collection) {
            if (!collectionUtil.checkIfCorrect(spaceMarine)) {
                throw new IncorrectCollectionException("Исходные данные в коллекции неверны, исправьте файл и попробуйте ещё раз");
            }
        }
    }

    /**
     * add spaceMarine to collection
     *
     * @param request - spaceMarine from client
     */
    @Override
    public CommandResult add(Request<?> request) {
        try {
            SpaceMarine spaceMarine = (SpaceMarine) request.type;
            collection.add(spaceMarine);
            return new CommandResult(true, "Новый элемент успешно добавлен");
        } catch (Exception exception) {
            return new CommandResult(false, "Передан аргумент другого типа");
        }
    }
//TODO write realization
    @Override
    public CommandResult sort(Request<?> request) {
        Collections.sort(collection);
        System.out.println("Коллекция отсортирована");
        return new CommandResult(true, collection.toString());    }
//TODO write realization
    @Override
    public CommandResult shuffle(Request<?> request) {
        Collections.shuffle(collection);
        System.out.println("Коллекция перемешена");
        return new CommandResult(true, collection.toString());
    }

    /**
     * @return information about collection
     */
    @Override
    public CommandResult show(Request<?> request) {
        return new CommandResult(true, information());
    }

    /**
     * clears the collection
     */
    @Override
    public CommandResult clear(Request<?> request) {
        collection.clear();
        return new CommandResult(true, "Элементы удалены");
    }
    /**
     * print info about collection : name, creation date, count of spaceMarines
     */
    @Override
    public CommandResult info(Request<?> request) {
        String inf = "Type of collection:" + collection.getClass().getSimpleName() + "\nDate of initialization:" + date + "\nNumbers of elements:" + collection.size();
        return new CommandResult(true, inf);
    }


    /**
     * print information about commands
     */
    @Override
    public CommandResult help(Request<?> request) {
            StringBuilder result = new StringBuilder();
            CommandManager commandManager = new CommandManager(new RequestManager());
            commandManager.getCommandMap().forEach((description, command) -> result.append(command.getDescription()).append("\n"));
            return new CommandResult(true, result.toString());
        }


    @Override
    public CommandResult exit(Request<?> request) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        atomicBoolean.set(true);
        return new CommandResult(true, "Теперь наши пути расходятся, как в море корабли");
    }
//TODO write realization
    @Override
    public CommandResult printFieldDescendingWeapon(Request<?> request) {
        ArrayList<Weapon> descendingWeapon = new ArrayList<>();
        for (SpaceMarine spaceMarine : collection) {
            Weapon weaponType = spaceMarine.getWeaponType();
            if (Collections.frequency(descendingWeapon, weaponType) == 0) {
                descendingWeapon.add(weaponType);
            }
        }
        Collections.sort(descendingWeapon, Collections.reverseOrder());
        return new CommandResult(true, String.valueOf(descendingWeapon));
    }
    /**
     * filter of spaceMarine whose weaponType matches with entered user's weaponType
     */
    @Override
    public CommandResult filterByWeapon(Request<?> request) {
        ArrayList<SpaceMarine> filterObj = new ArrayList<>();
        Weapon weaponType = (Weapon) request.type;
        for (SpaceMarine spaceMarine : collection) {
            if (spaceMarine.getWeaponType() == weaponType) {
                filterObj.add(spaceMarine);
                return new CommandResult(true, spaceMarine.toString());
            }
        } if (filterObj.isEmpty()){
            return new CommandResult(false, "Нет ни одного объекта с такой же характеристикой");
        }
        return new CommandResult(true, "Команда выполнена успешно");
    }
    @Override
    public CommandResult printUniqueMeleeWeapon(Request<?> request) {
        ArrayList<MeleeWeapon> uniq = new ArrayList<>();
        for (SpaceMarine spaceMarine : collection) {
            MeleeWeapon meleeWeapon = spaceMarine.getMeleeWeapon();
            if (Collections.frequency(uniq, meleeWeapon) == 0) {
                uniq.add(meleeWeapon);
            }
        }
        System.out.println("Выведены все уникальные значения");
        return new CommandResult(true, String.valueOf(uniq));
    }

    /**
     * check if the character exists, and if so, remove it from the collection
     *
     * @param request - id which client entered
     */
    @Override
    public CommandResult removeByID(Request<?> request) {
        String message = null;
        long ID;
        try {
            ID = Long.parseLong((String) request.type);
            if (collection.stream().noneMatch(spaceMarine -> spaceMarine.getId() == (ID)))
                return new CommandResult(false, "Персонажа с таким ID не существует");
            collection.removeIf(spaceMarine -> spaceMarine.getId() == (ID));
            return new CommandResult(true, "Персонаж успешно удален");
        } catch (NumberFormatException e) {
            message = "Вы неправильно ввели ID";
        }
        return new CommandResult(true, message);
    }

    /**
     * removes the healthiest person
     */
    @Override
    public CommandResult removeGreater(Request<?> request) {
        String message = null;
        try {
            float health = Float.parseFloat((String) request.type);
            if (health > 0) {
                collection.removeIf(spaceMarine -> spaceMarine.getHealth() > health);
                message = "Удален персонаж со здоровьем выше указанного";
            } else if (health < 0) {
                message = "Здоровье не может быть меньше нуля";
            }
        } catch (NumberFormatException e) {
            System.out.println("Уровень здоровья введен некорректно");
        }
        return new CommandResult(true, message);
    }



    /**
     * change the parameters of the character with the id entered by the client,
     * check if such a character exists
     */
    @Override
    public CommandResult update(Request<?> request) {
        String message = null;
        try {
            SpaceMarine spaceMarine = (SpaceMarine) request.type;
            if (getByID(spaceMarine.getId()) == null) {
                return new CommandResult(false, "Персонажа с таким ID не существует");
            }
            collection.stream()
                    .filter(spaceMarine1 -> spaceMarine1.getId() == spaceMarine.getId())
                    .forEach(spaceMarine1 -> spaceMarine1.update(spaceMarine));
            return new CommandResult(true, "Персонаж успешно обновлен");
        } catch (NumberFormatException e) {
            System.out.println("ID введен неверно");
        }
        return new CommandResult(true, message);
    }
    public boolean toHealth(float health) {
        boolean flag = true;
        for (SpaceMarine spaceMarine : collection) {
            if (health > spaceMarine.getHealth()) {
                flag = true;
            } else {
                flag = false;
            }
        }
        return flag;
    }
}





