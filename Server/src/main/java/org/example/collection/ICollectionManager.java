package org.example.collection;

import org.example.data.SpaceMarine;
import org.example.data.Weapon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public interface ICollectionManager {
    String info();
    void clear();
    void removeSpaceMarine(SpaceMarine spaceMarine);
    void removeSpaceMarines(Collection<SpaceMarine> collection);
    String information();
    void addSpaceMarine(SpaceMarine spaceMarine);
    void checkCollection();
    void updateId(SpaceMarine spaceMarine, Long id);
//    void removeById(Long id);
//    void getByID();
//    void removeGreater(Float health);
    LinkedList<SpaceMarine> sort();
    LinkedList<SpaceMarine> shuffle();

    ArrayList<SpaceMarine> filterByWeapon(Weapon weaponType);
//    void printUniqueMeleeWeapon();
//    void printFieldDescendingWeapon();
}
