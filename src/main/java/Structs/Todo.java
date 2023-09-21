package Structs;

import java.util.UUID;
import java.util.Vector;

public class Todo {
    // TODO: IMPLEMENTAR UPDATE

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

    public Todo(Vector<?> rowData) {
        this.id = (String) rowData.get(0);
        this.name = (String) rowData.get(1);
        this.description = (String) rowData.get(2);
        this.priority = (int) rowData.get(3);
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
