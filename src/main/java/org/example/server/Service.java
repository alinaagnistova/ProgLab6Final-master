package org.example.server;

import org.example.common.DataManager;
import org.example.common.network.CommandResult;
import org.example.common.network.Request;
import org.example.server.collection.CollectionManager;
import org.example.server.collection.CollectionUtil;

import javax.xml.bind.JAXBException;
import java.util.HashMap;

/**
 * The class is responsible for calling commands
 */
public class Service {
    private HashMap<String, Executable> commands = new HashMap<>();
    private CollectionManager collection = new CollectionManager();
    private CollectionUtil collectionUtil = new CollectionUtil();
    private DataManager dataManager;

    public Service(DataManager dataManager) {
        this.dataManager = dataManager;
        initCommands();
    }

    /**
     * add commands with a link to the method
     */
    private void initCommands() {
        commands.put("add", dataManager::add);
        commands.put("sort", dataManager::sort);
        commands.put("shuffle", dataManager::shuffle);
        commands.put("show", dataManager::show);
        commands.put("clear", dataManager::clear);
        commands.put("info", dataManager::info);
        commands.put("help", dataManager::help);
        commands.put("print_field_descending_weapon", dataManager::printFieldDescendingWeapon);
        commands.put("print_unique_melee_weapon", dataManager::printUniqueMeleeWeapon);
        commands.put("remove_by_id", dataManager::removeByID);
        commands.put("remove_greater", dataManager::removeGreater);
        commands.put("filter_by_weapon", dataManager::filterByWeapon);
        commands.put("update", dataManager::update);
        commands.put("exit", dataManager::exit);

    }

    /**
     * check if there is a command on the server and execute it
     * If we pass a collection, we save it
     * if the command is null and the collection is not passed,
     * then check the parameters for the commands update add_if_max add_if_min
     *
     * @param request request - command from client
     */
    public CommandResult executeCommand(Request<?> request) throws JAXBException {
        if (!commands.containsKey(request.command) && request.command != null)
            return new CommandResult(false, "Такой команды на сервере нет.");
        else if (request.command == null && request.collection != null) {
            collection.loadCollection(request.collection.getCollection());
            return new CommandResult(true, "правда");
        } else if (request.command == null) {
            if (collection.toHealth((float) request.type) || collectionUtil.checkExist((long) request.type)) {
                return new CommandResult(true, "правда");
            } else {
                return new CommandResult(false, "неправда");
            }
        }
        return commands.get(request.command).execute(request);
    }
}
