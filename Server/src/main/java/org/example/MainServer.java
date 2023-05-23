package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.collection.CollectionManager;
import org.example.command.*;
import org.example.error.ExitObligedException;
import org.example.managers.CommandManager;
import org.example.managers.FileManager;
import org.example.utils.*;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class MainServer extends Thread{
        public static int PORT = 6086;
        public static final int CONNECTION_TIMEOUT = 60 * 1000;
        private static final ReaderWriter console = new BlankConsole();

        static final Logger rootLogger = LogManager.getRootLogger();

        public static void main(String[] args) {
            if(args.length > 1){
                try{
                    PORT = Integer.parseInt(args[1]);
                } catch (NumberFormatException ignored) {}
            }
            CollectionManager collectionManager = new CollectionManager();
//            FileManager fileManager = new FileManager(console, collectionManager);
              FileManager fileManager = new FileManager();

            try{
                MainServer.rootLogger.info("Создание объектов");
                String link = args[0];
                File file = new File(link);
                if (file.exists() && !file.isDirectory()) {
                    fileManager.loadFromXml(args[0]);
                    collectionManager.checkCollection();
                    MainServer.rootLogger.info("Создание объектов успешно завершено");
                }else{
                    fileManager.readFileName();
                }
            } catch (JAXBException | FileNotFoundException e){
                console.write(ConsoleColors.toColor("До свидания!", ConsoleColors.YELLOW));
                MainServer.rootLogger.error("Ошибка во времени создания объектов");
                return;
            }

            CommandManager commandManager = new CommandManager(fileManager);
            commandManager.addCommand(List.of(
                    new HelpCommand(commandManager),
                    new InfoCommand(collectionManager),
                    new ShowCommand(collectionManager),
                    new AddCommand(collectionManager),
                    new UpdateIdCommand(collectionManager),
                    new RemoveByIdCommand(collectionManager),
                    new ClearCommand(collectionManager),
                    new ExecuteScriptCommand(),
                    new ExitCommand(),
                    new SortCommand(collectionManager),
                    new RemoveGreaterCommand(collectionManager),
                    new ShuffleCommand(collectionManager),
                    new FilterByWeaponCommand(collectionManager),
                    new PrintFieldDescendingWeapon(collectionManager),
                    new PrintUniqueMeleeWeaponCommand(collectionManager)
            ));
            MainServer.rootLogger.debug("Создан объект менеджера команд");
            RequestHandler requestHandler = new RequestHandler(commandManager);
            MainServer.rootLogger.debug("Создан объект обработчика запросов");
            Server server = new Server(PORT, CONNECTION_TIMEOUT, requestHandler, fileManager);
            MainServer.rootLogger.debug("Создан объект сервера");
            server.run();
        }
    }
