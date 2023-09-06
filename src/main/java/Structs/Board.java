package Structs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import Db.Database;

public class Board {
	private final Database storage;
	
	public Board() {
		this.storage = new Database();
	}
	
	public Todo addTask(Scanner scanner) {
		System.out.print("\nDigite o nome da sua tarefa: ");
		String taskName = scanner.nextLine();
		
		System.out.println("Digite um breve descrição para a terafa: ");
		String taskDescription = scanner.nextLine();
		System.out.println();
		
		Todo todo = new Todo(taskName, taskDescription);
		
		String sql = "INSERT INTO todo (id, name, description, priority) VALUES (?, ?, ?, ?)";
		
		try (
			Connection conn = storage.connect();
			PreparedStatement stmt = conn.prepareStatement(sql)
		){
			stmt.setString(1, todo.getId());
			stmt.setString(2, todo.getName());
			stmt.setString(3, todo.getDescription());
			
			stmt.executeUpdate();
		} catch (SQLException e){
			System.out.println(e.getMessage());
		}

		return todo;
	}
	
	public void getTask(Scanner scanner) {
		System.out.print("\nDigite o id da tarefa: ");
		String taskId = scanner.nextLine().trim();
		
		String sql = "SELECT name, description FROM todo WHERE id = ? LIMIT 1";
		
		try (
			Connection conn = storage.connect();
			PreparedStatement stmt = conn.prepareStatement(sql);
		){
			stmt.setString(1, taskId);
			ResultSet rs = stmt.executeQuery();
	        
			if (!rs.next()) {
				System.out.printf(
					"Id: %s não foi encontrado no banco!\n\n", 
					taskId
				);
				return;
			}
			
			System.out.printf(
				"\n=========== %s ===========\n%s\n\n", 
				rs.getString("name"), 
				rs.getString("description")
			);
		} catch (SQLException e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public void getTasks() {
		System.out.println("\n===================== TASKS ======================");
		
		try (
			Connection conn = storage.connect();
			Statement stmt = conn.createStatement();
		){
			ResultSet rs = stmt.executeQuery("SELECT id, name FROM todo");
	        
			while(rs.next())
	        {
	          System.out.printf(
	        	"%s - %s\n", 
	        	rs.getString("id"), 
	        	rs.getString("name")
	          );
	        }
			
		} catch (SQLException e){
			System.out.println(e.getMessage());
		}
	
		System.out.println("==================================================\n");
	}
	
	public void deleteTask(Scanner scanner) {
		System.out.print("\nDigite o id da tarefa: ");
		String taskId = scanner.nextLine().trim();
		
		String sql = "DELETE FROM todo WHERE id=?";
		
		try (
			Connection conn = storage.connect();
			PreparedStatement stmt = conn.prepareStatement(sql)
    ) {
			
			stmt.setString(1, taskId);
			int rowsAffected = stmt.executeUpdate();
			
			if (rowsAffected == 0) {
				System.out.println("Nenhum item foi deleteado!");
			} else {
				System.out.println("Task deletada!");
			}
			
		} catch (SQLException e){
			System.out.println(e.getMessage());
		}
	}
}
