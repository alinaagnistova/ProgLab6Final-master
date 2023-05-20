package org.example.collection;

import org.example.data.SpaceMarine;
import org.example.data.Weapon;

import java.util.Collection;

public interface ICollectionManager {
    String info();
    void clear();
    void removeSpaceMarine(SpaceMarine spaceMarine);
    void removeSpaceMarines(Collection<SpaceMarine> collection);
    String information();
    void addSpaceMarine(SpaceMarine spaceMarine);
    void checkCollection();
//    void updateId(SpaceMarine spaceMarine, Long id);
//    void removeById(Long id);
//    void getByID();
//    void shuffle();
//    void removeGreater(Float health);
//    void sort();
//    void filterByWeapon(Weapon weaponType);
//    void printUniqueMeleeWeapon();
//    void printFieldDescendingWeapon();
}
