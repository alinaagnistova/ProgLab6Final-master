package org.example.collection;

import org.example.data.SpaceMarine;
import org.example.managers.Validator;

/**
 * The class that realize methods for work with SpaceMarine
 */
public class CollectionUtil {
    static Validator validator = new Validator();

    /**
     * @param ID
     * @return
     */
        public static boolean checkExist(Long ID) {
            for (SpaceMarine spaceMarine:CollectionManager.getCollection()) {
                if (spaceMarine.getId().equals(ID))
                    return true;
            }
            return false;
        }
    /**
     * displays information about the character with all fields
     *
     * @param spaceMarine
     */
        public static String display(SpaceMarine spaceMarine) {
            return ("ID элемента коллекции – " + spaceMarine.getId() +
                    "\nИмя бойца – " + spaceMarine.getName() +
                    "\nКоордината X – " + spaceMarine.getCoordinates().getX() +
                    "\nКоордината Y – " + spaceMarine.getCoordinates().getY() +
                    "\nДата создания элемента – " + spaceMarine.getCreationDate() +
                    "\nУровень здоровья – " + spaceMarine.getHealth() +
                    "\nКатегория – " + spaceMarine.getCategory() +
                    "\nТип оружия – " + spaceMarine.getWeaponType() +
                    "\nТип оружия ближнего боя – " + spaceMarine.getMeleeWeapon() +
                    "\nНазвание дивизиона – " + spaceMarine.getChapter().getName() +
                    "\nКоличество бойцов дивизиона – " + spaceMarine.getChapter().getMarinesCount() +
                    "\n-----------------------------------------------------------------------------\n"
            );
        }
        public boolean checkIfCorrect(SpaceMarine spaceMarine){
            if (
                        validator.checkName(spaceMarine.getName()) ||
                        validator.checkCoordinateX(spaceMarine.getCoordinates().getX()) ||
                        validator.checkCoordinateY(spaceMarine.getCoordinates().getY()) ||
                        validator.checkHealth(spaceMarine.getHealth()) ||
                        validator.checkCategory(spaceMarine.getCategory()) ||
                        validator.checkWeapon(spaceMarine.getWeaponType()) ||
                        validator.checkMeleeWeapon(spaceMarine.getMeleeWeapon()) ||
                        validator.checkName(spaceMarine.getChapter().getName()) ||
                        validator.checkMarinesCount(spaceMarine.getChapter().getMarinesCount())
            ){
                return true;
            }
            return false;
        }
    }

