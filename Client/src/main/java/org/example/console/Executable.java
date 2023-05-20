package org.example.console;

import org.example.error.CommandRuntimeException;
import org.example.error.ExitObligedException;
import org.example.error.IllegalArgumentsException;

/**
 * Интерфейс для исполняемых команд
 */
public interface Executable {
    void execute(String args) throws CommandRuntimeException, ExitObligedException, IllegalArgumentsException;
}
