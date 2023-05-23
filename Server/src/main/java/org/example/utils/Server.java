package org.example.utils;

import org.example.dtp.Request;
import org.example.dtp.Response;
import org.example.error.ConnectionErrorException;
import org.example.error.OpeningServerException;
import org.example.managers.FileManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


public class Server {
    private int port;
    private int soTimeout;
    private ReaderWriter console;
    private ServerSocketChannel ss;
    private SocketChannel socketChannel;
    private RequestHandler requestHandler;

    static final Logger serverLogger = LogManager.getLogger(Server.class);

    BufferedInputStream bf = new BufferedInputStream(System.in);

    BufferedReader scanner = new BufferedReader(new InputStreamReader(bf));
    private FileManager fileManager;

    public Server(int port, int soTimeout, RequestHandler handler, FileManager fileManager) {
        this.port = port;
        this.soTimeout = soTimeout;
        this.console = new BlankConsole();
        this.requestHandler = handler;
        this.fileManager = fileManager;
    }

    public void run(){
        try{
            openServerSocket();
            serverLogger.info("Создано соединение с клиентом");
            while (true) {
                try {
                    if (scanner.ready()) {
                        String line = scanner.readLine();
                        // TODO: when we close app line == null
                        if (line.equals("save")) {
                            FileManager.saveToXml();
                            serverLogger.info("Коллекция сохранена");
                        }
                    }
                } catch (IOException ignored) {}
                try (SocketChannel clientSocket = connectToClient()) {
                    if(!processClientRequest(clientSocket)) break;
                } catch (ConnectionErrorException | SocketTimeoutException ignored) {
                } catch (IOException exception) {
                    console.printError("Произошла ошибка при попытке завершить соединение с клиентом!");
                    serverLogger.error("Произошла ошибка при попытке завершить соединение с клиентом!");
                }
            }
            stop();
            serverLogger.info("Соединение закрыто");
        } catch (OpeningServerException e) {
            console.printError("Сервер не может быть запущен");
            serverLogger.fatal("Сервер не может быть запущен");
        } catch (NullPointerException ex){
            serverLogger.warn("OMG!!! SEEMS LIKE FUCKED UP ^_^");
        }
    }

    private void openServerSocket() throws OpeningServerException{
        try {
            SocketAddress socketAddress = new InetSocketAddress(port);
            serverLogger.debug("Создан сокет");
            ss = ServerSocketChannel.open();
            serverLogger.debug("Создан канал");
            ss.bind(socketAddress);
            serverLogger.debug("Открыт канал сокет");
        } catch (IllegalArgumentException exception) {
            console.printError("Порт '" + port + "' находится за пределами возможных значений!");
            serverLogger.error("Порт находится за пределами возможных значений");
            throw new OpeningServerException();
        } catch (IOException exception) {
            serverLogger.error("Произошла ошибка при попытке использовать порт");
            console.printError("Произошла ошибка при попытке использовать порт '" + port + "'!");
            throw new OpeningServerException();
        }
    }

    private SocketChannel connectToClient() throws ConnectionErrorException, SocketTimeoutException {
        try {
//            console.println("Прослушивание порта '" + port + "'...");
//            serverLogger.info("Прослушивание порта '" + port + "'...");
            ss.socket().setSoTimeout(100);
            socketChannel = ss.socket().accept().getChannel();
            console.write("Соединение с клиентом успешно установлено.");
            serverLogger.info("Соединение с клиентом успешно установлено.");
            return socketChannel;
        } catch (SocketTimeoutException exception) {
            throw new SocketTimeoutException();
        } catch (IOException exception) {
            serverLogger.fatal("Произошла ошибка при соединении с клиентом!");
            throw new ConnectionErrorException();
        }
    }

    private boolean processClientRequest(SocketChannel clientSocket) {
        Request userRequest = null;
        Response responseToUser = null;
        try {
            ObjectInputStream clientReader = new ObjectInputStream(clientSocket.socket().getInputStream());
            ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.socket().getOutputStream());
            serverLogger.info("Открыты потоки ввода вывода");
            do {
                userRequest = (Request) clientReader.readObject();
                serverLogger.info("Получен реквест с командой" + userRequest.getCommandName(), userRequest);
                console.write(userRequest.toString());
                responseToUser = requestHandler.handle(userRequest);
                clientWriter.writeObject(responseToUser);
                serverLogger.info("Отправлен ответ", responseToUser);
                clientWriter.flush();
            } while (true);
        } catch (ClassNotFoundException exception) {
            console.printError("Произошла ошибка при чтении полученных данных!");
            serverLogger.fatal("Произошла ошибка при чтении полученных данных!");
        } catch (InvalidClassException | NotSerializableException exception) {
            console.printError("Произошла ошибка при отправке данных на клиент!");
            serverLogger.error("Произошла ошибка при отправке данных на клиент!");
        } catch (IOException exception) {
            if (userRequest == null) {
                console.printError("Непредвиденный разрыв соединения с клиентом!");
                serverLogger.error("Непредвиденный разрыв соединения с клиентом!");
            } else {
                console.write("Клиент успешно отключен от сервера!");
                serverLogger.info("Клиент успешно отключен от сервера!");
            }
        }
        return true;
    }

    private void stop() {
        class ClosingSocketException extends Exception{}
        try{
            if (socketChannel == null) throw new ClosingSocketException();
            socketChannel.close();
            ss.close();
            serverLogger.info("все соединения закрыты");
        } catch (ClosingSocketException exception) {
            console.printError("Невозможно завершить работу еще не запущенного сервера!");
            serverLogger.fatal("Невозможно завершить работу еще не запущенного сервера!");
        } catch (IOException exception) {
            console.printError("Произошла ошибка при завершении работы сервера!");
            serverLogger.fatal("Произошла ошибка при завершении работы сервера!");
        }
    }
}
