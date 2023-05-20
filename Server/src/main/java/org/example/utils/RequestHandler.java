package org.example.utils;

import org.example.dtp.Request;
import org.example.dtp.Response;
import org.example.dtp.ResponseStatus;
import org.example.error.CommandRuntimeException;
import org.example.error.ExitObligedException;
import org.example.error.IllegalArgumentsException;
import org.example.error.NoSuchCommandException;
import org.example.managers.CommandManager;

public class RequestHandler {
    private CommandManager commandManager;

    public RequestHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public Response handle(Request request) {
        try {
            return commandManager.execute(request);
        } catch (IllegalArgumentsException e) {
            return new Response(ResponseStatus.WRONG_ARGUMENTS,
                    "Неверное использование аргументов команды");
        } catch (CommandRuntimeException e) {
            return new Response(ResponseStatus.ERROR,
                    "Ошибка при исполнении программы");
        } catch (NoSuchCommandException e) {
            return new Response(ResponseStatus.ERROR, "Такой команды нет в списке");
        } catch (ExitObligedException e) {
            return new Response(ResponseStatus.EXIT);
        }
    }
}