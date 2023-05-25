package org.example.utils;

import org.example.console.*;
import org.example.data.AstartesCategory;
import org.example.data.MeleeWeapon;
import org.example.data.Weapon;
import org.example.error.FileModeException;

import java.util.Arrays;
/**
 * The class is responsible for what the user enters
 */
public class ReadManager implements Readable {
    private final ReaderWriter console;
    private final UserInput scanner;

    public ReadManager(ReaderWriter console) {
        this.console = (Console.isFileMode())
                ? new BlankConsole()
                : console;
        this.scanner = (Console.isFileMode())
                ? new ExecuteFileManager()
                : new ConsoleInput();
    }

    /**
     * method checks if the name is entered correctly, it contains only letters or not
     *
     * @return name
     */
    @Override
    public String readName() {
            String name;
            while (true) {
                console.write("Введите имя/название отряда:");
                name = scanner.nextLine().trim();
                if (name.equals("") || !name.matches("^[a-zA-Z-А-Яа-я]*$")) {
                    console.printError("Имя не может быть пустой строкой/иными знаками, кроме букв");
                    if (Console.isFileMode()) throw new FileModeException();
//                    System.out.println("Имя не может быть пустой строкой, введите имя");
//                    name = consoleManager.readLine();
//                } else if (!name.matches("^[a-zA-Z-А-Яа-я]*$")) {
//                    System.out.println("Имя не может быть иными знаками кроме букв");
//                    name = consoleManager.readLine();
                } else {
                    return name;
                }
            }
        }

    /**
     * checks if the entered coordinate is correct, whether a number is entered or not
     *
     * @return X
     */
    @Override
    public Integer readCoordinateX() {
        while (true) {
            console.write("Введите координату X:");
            String input = scanner.nextLine().trim();
            try {
                int x = Integer.parseInt(input);
                if (x <= -595){
                    console.printError("Значение поля должно быть больше -595");
                }
                if (input.isEmpty()){
                    console.printError("Введите число, а не пустую строку");
                }else{
                return x;}
            } catch (NumberFormatException exception) {
                console.printError("Число введено неверно");
                if (Console.isFileMode()) throw new FileModeException();
            }
        }
//
//        consoleManager.write("Введите координату X:");
////        System.out.println("Введите координату X:");
//        while (true) {
//            try {
//                String Xstring = consoleManager.readLine();
//                int X = Integer.parseInt(Xstring);
//                if (X <= -595) {
//                    System.out.println("Значение поля должно быть больше -595, попробуйте ввести значение снова");
//                    continue;
//                    if (Console.isFileMode()) throw new FileModeException();
//                }
//                if (!Xstring.equals("")) {
//                    return X;
//                } else {
//                    System.out.println("Вы должны ввести число, а не пустую строку");
//                    if (Console.isFileMode()) throw new FileModeException();
//
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Число введено неверно");
//            }
//        }
    }
    /**
     * checks if the entered coordinate is correct, whether a number is entered or not
     *
     * @return Y
     */
    @Override
    public Float readCoordinateY() {
        while (true) {
            console.write("Введите координату Y:");
            String input = scanner.nextLine().trim();
            try {
                float y = Float.parseFloat(input);
                if (input.isEmpty()) {
                    console.printError("Введите число, а не пустую строку");
                } else {
                    return y;
                }
            } catch (NumberFormatException exception) {
                console.printError("Число введено неверно");
                if (Console.isFileMode()) throw new FileModeException();
            }
        }
    }
//        if (response.getStatus() == ResponseStatus.EXECUTE_SCRIPT) {
//            String Ystring = consoleManager.readLine();
//            Float Y = Float.parseFloat(Ystring);
//            return Y;
//        } else {
//            System.out.println("Введите координату Y:");
//            while (true) {
//                try {
//                    String Ystring = consoleManager.readLine();
//                    Float Y = Float.parseFloat(Ystring);
//                    if (!Ystring.equals("")) {
//                        return Y;
//                    } else {
//                        System.out.println("Вы должны ввести число, а не пустую строку");
//                    }
//                } catch (NumberFormatException e) {
//                    System.out.println("Число введено неверно");
//                }
//            }


    /**
     * checks the correctness of the entered health, whether the number is entered or not, check for zero
     *
     * @return health
     */
    @Override
    public Float readHealth() {
        while (true) {
            console.write("ведите уровень здоровья бойца:");
            String input = scanner.nextLine().trim();
            try {
                float health = Float.parseFloat(input);
                if (input.isEmpty()) {
                    console.printError("Введите число, а не пустую строку");
                } else {
                    return health;
                }
            } catch (NumberFormatException exception) {
                console.printError("Число введено неверно");
                if (Console.isFileMode()) throw new FileModeException();
            }
        }
//        if (response.getStatus() == ResponseStatus.EXECUTE_SCRIPT) {
//            String healthString = consoleManager.readLine();
//            float health = Float.parseFloat(healthString);
//            return health;
//        } else {
//            System.out.println("Введите уровень здоровья бойца:");
//            while (true) {
//                String healthString = consoleManager.readLine();
//                try {
//                    float health = Float.parseFloat(healthString);
//                    if (health > 0) {
//                        return health;
//                    } else {
//                        System.out.println("Уровень здоровье должен быть больше 0");
//                    }
//                } catch (NumberFormatException e) {
//                    System.out.println("Число введено неверно");
//                }
//            }
        }

    @Override
    public AstartesCategory readCategory() {
        console.write("Вы должны ввести одно из перечисленных видов оружия:" + Arrays.toString(AstartesCategory.values()));
        AstartesCategory astartesCategory;
        try{
            astartesCategory = AstartesCategory.valueOf(console.getValidatedValue("\nВведите вид оружия:").toUpperCase());
        }catch (IllegalArgumentException e){
            astartesCategory = readCategory();
            if (Console.isFileMode()) throw new FileModeException();
        }
        return astartesCategory;
    }
    @Override
    public Weapon readWeapon(){
        console.write("Вы должны ввести одно из перечисленных видов оружия:" + Arrays.toString(Weapon.values()));
        Weapon weapon;
        try {
            weapon = Weapon.valueOf(console.getValidatedValue("\nВведите вид оружия:").toUpperCase());
        }catch (IllegalArgumentException e){
            weapon = readWeapon();
            if (Console.isFileMode()) throw new FileModeException();
        }
        return weapon;
    }
    @Override
    public MeleeWeapon readMeleeWeapon() {
        console.write("Вы должны ввести одно из перечисленных видов оружия ближнего боя:" + Arrays.toString(MeleeWeapon.values()));
        MeleeWeapon meleeWeapon;
        try {
            meleeWeapon = MeleeWeapon.valueOf(console.getValidatedValue("\nВведите вид оружия ближнего боя:").toUpperCase());
        }catch (IllegalArgumentException e){
            meleeWeapon = readMeleeWeapon();
            if (Console.isFileMode()) throw new FileModeException();
        }
        return meleeWeapon;
    }
    @Override
    public Integer readChapterMarinesCount(){
        while (true) {
            console.write("Введите количество бойцов дивизиона:");
            String input = scanner.nextLine().trim();
            try {
                int marinesCount = Integer.parseInt(input);
                if (input.isEmpty()) {
                    console.printError("Введите число, а не пустую строку");
                } else {
                    return marinesCount;
                }
            } catch (NumberFormatException exception) {
                console.printError("Число введено неверно");
                if (Console.isFileMode()) throw new FileModeException();
            }
        }
//        consoleManager.write("Введите количество бойцов в отряде:");
//        while (true) {
//            String marinesCountString = consoleManager.readLine();
//            try {
//                int marinesCount = Integer.parseInt(marinesCountString);
//                if (marinesCount >= 0) {
//                    return marinesCount;
//                } else {
//                    System.out.println("Количество бойцов должно быть не меньше 0");
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Число введено неверно");
//            }
//        }
    }

//    /**
//     * checks the existence of the entered category
//     *
//     * @param categories
//     * @return boolean true or false
//     */
//    public static boolean doesThisCategoryExist(String categories) {
//        for (AstartesCategory category : AstartesCategory.values()) {
//            if (category.name().equals(categories)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//    /**
//     * checks the existence of the entered weapon
//     *
//     * @param weapons
//     * @return boolean true or false
//     */
//    public static boolean doesThisWeaponExist(String weapons) {
//        for (Weapon weapon : Weapon.values()) {
//            if (weapon.name().equals(weapons)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//    /**
//     * checks the existence of the entered color
//     *
//     * @param meleeWeapons
//     * @return boolean true or false
//     */
//    public static boolean doesThisMeleeWeaponExist(String meleeWeapons) {
//        for (MeleeWeapon meleeWeapon : MeleeWeapon.values()) {
//            if (meleeWeapon.name().equals(meleeWeapons)) {
//                return true;
//            }
//        }
//        return false;
//    }
}

