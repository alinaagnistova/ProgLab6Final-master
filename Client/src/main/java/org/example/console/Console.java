package org.example.console;



import org.example.utils.ConsoleColors;

import java.util.Scanner;

public class Console implements ReaderWriter {
private static boolean fileMode = false;

    public Console() {

    }

    public static boolean isFileMode() {
        return fileMode;
    }

    public static void setFileMode(boolean fileMode) {
        Console.fileMode = fileMode;
    }

    @Override
    public Long readLong() {
        Scanner scanner = new Scanner(System.in);
        Long number = Long.valueOf(scanner.nextLine().trim());
        return number;
    }

    @Override
    public String readLine() {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine().trim();
        return text;
    }

    @Override
    public void write(String text) {
        System.out.print(text);
    }

    @Override
    public void write(String text, ConsoleColors consoleColors) {
        System.out.println(ConsoleColors.toColor(text, consoleColors));
    }
    @Override
    public void printError(String text) {
        System.out.println(ConsoleColors.RED + text + ConsoleColors.RESET);
    }

    @Override
    public String getValidatedValue(String message) {
        write(message);
        while (true) {
            String userPrint = readLine();
            if (!userPrint.isEmpty() && !userPrint.isBlank()) {
                return userPrint;
            }
        }

    }
//    public void fileRead() throws JAXBException, FileNotFoundException {
//        boolean flag = false;
//        try {
//            while (!flag) {
//                System.out.println("Введите название файла еще раз");
//                Scanner scanner = new Scanner(System.in);
//                File file = new File(scanner.nextLine());
//                if (file.exists() && !file.isDirectory()) {
//                    flag = true;
//                    collectionManager.setCollection(loadFromXml(file.getName()).getCollection());
//                    Request<CollectionManager> request = new Request<>(null, collectionManager, collectionManager);
//                    CollectionManager result = requestManager.sendCollection(request);
//                    result.getCollection();
//                    collectionManager.setCollection(result.getCollection());
//                }
//            }
//        } catch (IllegalArgumentException | JAXBException | IOException | ClassNotFoundException e) {
//            System.out.println("Файл не найден");
//        }
//    }
//    }
//    @Override
//    public File readFileName(){
//        Scanner scanner = new Scanner(System.in);
//        String path = scanner.nextLine();
//        File file = new File(String.valueOf(path));
//        return file;
//    }

}