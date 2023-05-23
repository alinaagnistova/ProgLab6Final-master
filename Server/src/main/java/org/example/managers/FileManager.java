package org.example.managers;

import org.example.collection.CollectionManager;

import javax.xml.bind.*;
import java.io.*;
import java.util.Scanner;

public final class FileManager {

    private static String fileName;

    public FileManager(String fileName) {
        fileName = this.fileName;
    }

    public FileManager() {
    }

    /**
     * converts JavaObject to XML file
     *
     * @throws JAXBException
     * @throws IOException
     */
    public static void saveToXml() {
        try {
            CollectionManager collectionManager = new CollectionManager();
            JAXBContext jaxbContext = JAXBContext.newInstance(CollectionManager.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            marshaller.marshal(collectionManager, writer);
            writer.close();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * converts XML to JavaObject
     *
     * @return unmarshal file
     * @throws JAXBException
     */
    public static CollectionManager loadFromXml(String fileName) throws FileNotFoundException, JAXBException {
        FileManager.fileName = fileName;
        try {
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(fileName));
            JAXBContext jaxbContext = JAXBContext.newInstance(CollectionManager.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (CollectionManager) unmarshaller.unmarshal(inputStream);
        } catch (JAXBException e) {
            System.out.println("С файлом что-то не так, либо он пуст. В коллекции нет исходных данных");
            return new CollectionManager();
        } catch (FileNotFoundException ex) {
            System.out.println("Указанный файл не найден");
        }
        return null;
    }

    public void readFileName() {
        while (true) {
            try {
                System.out.println("Введите название файла еще раз");
                Scanner scanner = new Scanner(System.in);
                String fileName = scanner.nextLine();
                    File file = new File(fileName);
                if (file.exists() && !file.isDirectory()) {
                    FileManager.loadFromXml(fileName);
                    break;
                }else{
                    readFileName();
                }
                } catch (JAXBException e) {
                System.out.println("С файлом что-то не так, либо он пуст");
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден");
            }
        }
    }
}



