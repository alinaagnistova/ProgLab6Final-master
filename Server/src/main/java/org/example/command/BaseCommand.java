package org.example.command;



public abstract class BaseCommand implements Executable{
    private final String name;
    private final String description;
//    ClientManager clientManager = new ClientManager();
    private Object argument;

    public BaseCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Object getArgument() {
        return argument;
    }

    public void setArgument(Object argument) {
        this.argument = argument;
    }

    @Override
    public String toString() {
        return  name + ":" + description;
    }
}
