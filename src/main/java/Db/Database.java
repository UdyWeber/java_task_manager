package Db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	public Connection connect() {
		Connection conn = null;

		try {
			String connectionString = "jdbc:sqlite:app.db";

			conn = DriverManager.getConnection(connectionString);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return conn;
	}

	public void createSchema() {
		try (
				Connection conn = this.connect();
				Statement stmt = conn.createStatement();
		) {
			String todoTable = """
				CREATE TABLE IF NOT EXISTS todo (
				  id TEXT PRIMARY KEY,
				  name TEXT NOT NULL,
				  description TEXT NOT NULL,
				  priority int NOT NULL
				);
        	""";

			stmt.execute(todoTable);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}
}
