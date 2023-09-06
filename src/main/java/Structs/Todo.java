package Structs;

import java.util.UUID;

public class Todo {
	// TODO: IMPLEMENTAR UPDATE
	
	private String id;
	private String name;
	private String description;

	public Todo(String name, String description) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.description = description;
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
