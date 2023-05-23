package org.example.collection;

import org.example.data.MeleeWeapon;
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
    boolean checkCollection();
    void updateId(SpaceMarine spaceMarine, Long id);
//    void removeById(Long id);
//    void getByID();
//    void removeGreater(Float health);
    void sort();
    void shuffle();

    ArrayList<SpaceMarine> filterByWeapon(Weapon weaponType);
    ArrayList<MeleeWeapon> printUniqueMeleeWeapon();
    ArrayList<Weapon> printFieldDescendingWeapon();
}
