package org.example.collection;

import org.example.data.*;
import org.example.error.IncorrectCollectionException;

import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import org.apache.logging.log4j.Logger;
/**
 * The class that implements collection related methods
 */
@XmlRootElement
@XmlSeeAlso({SpaceMarine.class})
@XmlAccessorType(XmlAccessType.FIELD)
public class CollectionManager implements ICollectionManager, Serializable {
    CollectionUtil collectionUtil = new CollectionUtil();
    private String filename = "s";
    static final Logger collectionManagerLogger = LogManager.getLogger(CollectionManager.class);


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
//
//    /**
//     * upload the collection to the server
//     *
//     * @param request - collection
//     */
//    public void loadCollection(LinkedList<SpaceMarine> request) throws JAXBException {
//        setCollection(request);
//    }

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

    /**
     * print info about collection
     */
    @Override
    public String info() {
        String info = "Type of collection:" + collection.getClass().getSimpleName() + "\nDate of initialization:" + date + "\nNumbers of elements:" + collection.size();
        return info;
    }

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
        spaceMarine.setId(GenerationID.generateID());
        collection.add(spaceMarine);
        collectionManagerLogger.info("Добавлен объект в коллекцию", spaceMarine);
    }

    /**
     * updates data of spaceMarine, ID stays the same
     *
     * @param newSpaceMarine
     * @param ID
     */
    @Override
    public void updateId(SpaceMarine newSpaceMarine, Long ID) {
        for (SpaceMarine spaceMarine : collection) {
            if (spaceMarine.getId().equals(ID)) {
                spaceMarine.setName(newSpaceMarine.getName());
                spaceMarine.setCoordinates(newSpaceMarine.getCoordinates());
                spaceMarine.setHealth(newSpaceMarine.getHealth());
                spaceMarine.setChapter(newSpaceMarine.getChapter());
                if (spaceMarine.getCategory() != null) {
                    spaceMarine.setCategory(newSpaceMarine.getCategory());
                }
                if (spaceMarine.getWeaponType() != null) {
                    spaceMarine.setWeaponType(newSpaceMarine.getWeaponType());
                }
                if (spaceMarine.getMeleeWeapon() != null) {
                    spaceMarine.setMeleeWeapon(newSpaceMarine.getMeleeWeapon());
                }
            }
        }
    }

    @Override
    public void removeSpaceMarine(SpaceMarine spaceMarine) {
        collection.remove(spaceMarine);
        collectionManagerLogger.info("Элемент удалён");
    }
    @Override
    public void removeSpaceMarines(Collection<SpaceMarine> collection) {
        this.collection.removeAll(collection);
        }

    /**
     * clear collection
     *
     */
    @Override
    public void clear() {
        collection.clear();
        collectionManagerLogger.info("Коллекция очищена");
    }

    /**
     * shuffle collection
     */
    @Override
    public void shuffle() {
        Collections.shuffle(collection);
    }

//    /**
//     * removes the healthiest person
//     *
//     * @param health
//     */
//    @Override
    public void removeGreater(Float health) {
        Iterator<SpaceMarine> iterator = collection.iterator();
        while (iterator.hasNext()) {
            SpaceMarine spaceMarine = iterator.next();
            {
                if (spaceMarine.getHealth() > health) {
                    iterator.remove();
                    System.out.println("Элемент удален из коллекции: " + spaceMarine.getName());
                } else if (!iterator.hasNext()) {
                    System.out.println("Нет элементов с таким же уровнем здоровья");
                }
            }
        }
    }
    public void removeById(Long ID) {
        Iterator<SpaceMarine> iterator = collection.iterator();
        while (iterator.hasNext()) {
            SpaceMarine spaceMarine = iterator.next();
            {
                if (spaceMarine.getId().equals(ID)) {
                    iterator.remove();
                    System.out.println("Элемент удален из коллекции");
                } else if (!iterator.hasNext()) {
                    System.out.println("Элемента с таким ID не существует");
                }
            }
        }
    }

    /**
     * sort collection
     */
    @Override
    public void sort() {
        Collections.sort(collection);
    }

    /**
     * show elements with this kind of weapon
     *
     * @param weaponType
     */
    @Override
    public ArrayList<SpaceMarine> filterByWeapon(Weapon weaponType) {
        ArrayList<SpaceMarine> filterObjects = new ArrayList<>();
        for (SpaceMarine spaceMarine : collection) {
            if (spaceMarine.getWeaponType() == weaponType) {
                filterObjects.add(spaceMarine);
            }
        }
        if (filterObjects.size() == 0) {
            System.out.println("Нет ни одного экземпляра с таким полем");
        }
        return filterObjects;
//            filterObjects.forEach(CollectionUtil::display);
        }


    /**
     * find unique meleeWeapons and print them
     */
    @Override
    public ArrayList<MeleeWeapon> printUniqueMeleeWeapon() {
        ArrayList<MeleeWeapon> uniq = new ArrayList<>();
        for (SpaceMarine spaceMarine : collection) {
            MeleeWeapon meleeWeapon = spaceMarine.getMeleeWeapon();
            if (Collections.frequency(uniq, meleeWeapon) == 0) {
                uniq.add(meleeWeapon);
            }
        }
        return uniq;
    }

    /**
     * find all weapons in collection and print it (descending)
     */
    @Override
    public ArrayList<Weapon> printFieldDescendingWeapon() {
        ArrayList<Weapon> descendingWeapon = new ArrayList<>();
        for (SpaceMarine spaceMarine : collection) {
            Weapon weaponType = spaceMarine.getWeaponType();
            if (Collections.frequency(descendingWeapon, weaponType) == 0) {
                descendingWeapon.add(weaponType);
            }
        }
        Collections.sort(descendingWeapon, Collections.reverseOrder());
        return descendingWeapon;
    }
@Override
    public boolean checkCollection() {
        for (SpaceMarine spaceMarine : collection) {
            if (!collectionUtil.checkIfCorrect(spaceMarine)) {
                throw new IncorrectCollectionException("Исходные данные в коллекции неверны, исправьте файл и попробуйте ещё раз");
            }
        }
        return true;
    }

}





