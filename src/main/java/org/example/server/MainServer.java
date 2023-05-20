package org.example.server;

import org.example.common.Configuration;
import org.example.common.DataManager;
import org.example.common.data.SpaceMarine;
import org.example.common.network.CommandResult;
import org.example.common.network.Request;
import org.example.server.collection.CollectionManager;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The class accepts requests from the client, connects to it, and starts executing commands
 */
public class MainServer {
    private static int port = Configuration.PORT;

    /**
     * Start server, connect to the client and get requests with a collection and commands, execute them
     * @param args - port
     */
    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception exception) {
                System.out.println("Не получается спарсить порт. Используется " + port);
            }
        }
        DataManager dataManager;
        CollectionManager collection = new CollectionManager();
        SpaceMarine spaceMarine = new SpaceMarine();
        dataManager = collection;

        ServerSocketChannel serverSocketChannel;

        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);

            System.out.println("Сервер запущен. Порт: " + port);
        } catch (IOException exception) {
            System.out.println("Ошибка запуска сервера!");
            System.out.println(exception.getMessage());
            return;
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Выход");
            save(dataManager, "s");
        }));

        Service service = new Service(dataManager);

        AtomicBoolean exit = new AtomicBoolean(false);
        getUserInputHandler(dataManager, exit).start();

        while (!exit.get()) {
            try (SocketChannel socketChannel = serverSocketChannel.accept()) {
                if (socketChannel == null) continue;
                ObjectInputStream objectInputStream = new ObjectInputStream(socketChannel.socket().getInputStream());
                Request<CollectionManager> request = (Request<CollectionManager>) objectInputStream.readObject();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketChannel.socket().getOutputStream());
                CommandResult result = service.executeCommand(request);
                if (request.type == collection || request.type != null && request.collection != null) {
                    result.setSpaceMarineCollection(request.collection);
                    collection.loadCollection(request.collection.getCollection());
                    collection.getCollection();
                }
                System.out.println(socketChannel.getRemoteAddress() + ": " + request.command);

                if (result.status)
                    System.out.println("Команда выполнена успешно");
                else
                    System.out.println("Команда выполнена неуспешно");
                objectOutputStream.writeObject(result);

            } catch (IOException | ClassNotFoundException | JAXBException e) {
                System.out.println("Что-то пошло не так..");

            }

        }

    }

    /**
     * Running two commands on the server
     * save and exit
     *
     * @param dataManager - class with commands
     * @param exit        - command exit
     * @return
     */
    private static Thread getUserInputHandler(DataManager dataManager, AtomicBoolean exit) {
        return new Thread(() -> {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                if (scanner.hasNextLine()) {
                    String serverCommand = scanner.nextLine();
                    if (serverCommand.contains("save")) {
                        serverCommand = serverCommand.split(" ")[1];
                        save(dataManager, String.valueOf(serverCommand));
                        return;
                    }
                    if (serverCommand.equals("exit")) {
                        exit.set(true);
                        return;
                    } else {
                        System.out.println("Такой команды нет");
                    }
                } else {
                    exit.set(true);
                }
            }
        });
    }

    /**
     * save collection to file
     *
     * @param filename - file in which server load collection, s - default
     */
    private static void save(DataManager dataManager, String filename) {
        dataManager.save(filename);
    }

}