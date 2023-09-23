package Structs;

import java.util.UUID;

public class Todo {
    private String id;
    private String name;
    private String description;
    private int priority;

    public Todo(String name, String description, int priority) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.priority = priority;
    }

    public Object[] getAsTableRow() {
        return new Object[] {this.id, this.name, this.description, this.priority};
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }
}
