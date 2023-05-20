package org.example.command;



import org.example.dtp.Request;
import org.example.dtp.Response;
import org.example.error.CommandRuntimeException;
import org.example.error.ExitObligedException;
import org.example.error.IllegalArgumentsException;

import java.util.Scanner;

/**
 * Command update id {element} : update the value of the collection item whose id is equal to the given one
 */
public class UpdateIdCommand extends BaseCommand {
    public UpdateIdCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public Response execute(Request request) throws CommandRuntimeException, ExitObligedException, IllegalArgumentsException {
        return null;
    }
}




