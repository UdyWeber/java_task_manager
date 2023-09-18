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

    public void addTask(Scanner scanner) {
        System.out.print("\nDigite o nome da sua tarefa: ");
        String taskName = scanner.nextLine();

        System.out.print("Digite uma prioridade para a TASK [DEFAULT 1]: ");
        String taskPriority = scanner.nextLine().trim();

        int numTaskPriority = 1;

        if (!taskPriority.isEmpty()) {
            try {
                numTaskPriority = Integer.parseInt(taskPriority);
            } catch (Exception e) {
                System.out.println("Valor não númerico informado por favor tente novamente!\n");
                return;
            }
        }

        System.out.println("Digite um breve descrição para a tarefa: ");
        String taskDescription = scanner.nextLine();

        System.out.println();

        Todo todo = new Todo(taskName, taskDescription);

        String sql = "INSERT INTO todo (id, name, description, priority) VALUES (?, ?, ?, ?)";

        try (
            Connection conn = storage.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, todo.getId());
            stmt.setString(2, todo.getName());
            stmt.setString(3, todo.getDescription());
            stmt.setInt(4, numTaskPriority);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void getTask(Scanner scanner) {
        System.out.print("\nDigite o id da tarefa: ");
        String taskId = scanner.nextLine().trim();

        String sql = "SELECT name, description, priority FROM todo WHERE id = ? LIMIT 1";

        try (
            Connection conn = storage.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, taskId);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.printf(
                    "Id: %s não foi encontrado no banco!\n\n",
                    taskId
                );
                return;
            }

            String taskName = rs.getString("name");
            StringBuffer descriptionBuffer = new StringBuffer(rs.getString("description"));

            int delimiterLength = 22 + 2 + taskName.length();

            int timesToBreak = descriptionBuffer.length() / delimiterLength;
            int offSet = delimiterLength - 1;

            for (int i = 0; i < timesToBreak; i++) {
                int emptyCharIndex = descriptionBuffer.lastIndexOf(" ", offSet);

                if (emptyCharIndex != -1) {
                    descriptionBuffer.insert(emptyCharIndex + 1, "\n");
                } else {
                    descriptionBuffer.insert(offSet, "\n");
                }


                offSet += delimiterLength;
                timesToBreak = descriptionBuffer.length() / delimiterLength;
            }

            String bottomDelimiter = "=".repeat(delimiterLength);

            System.out.printf(
                """
                    
                    =========== %s ===========
                    %s
                                    
                    Priority: %d
                    %s
                                        
                    """,
                taskName,
                descriptionBuffer,
                rs.getInt("priority"),
                bottomDelimiter
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void listTasks() {
        System.out.println("\n===================== TASKS ======================");

        try (
            Connection conn = storage.connect();
            Statement stmt = conn.createStatement()
        ) {
            ResultSet rs = stmt.executeQuery("SELECT id, name, priority FROM todo ORDER BY priority DESC");

            while (rs.next()) {
                System.out.printf(
                    "%s - %s - %d\n",
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getInt("priority")
                );
            }

        } catch (SQLException e) {
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
                System.out.println("Nenhum item foi deletado!\n");
            } else {
                System.out.println("Task deletada!\n");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateTask(Scanner scanner) {
        System.out.print("\nDigite o id da tarefa: ");
        String taskId = scanner.nextLine().trim();

        if (!TaskExistsById(taskId)) {
            System.out.println("Task não foi encontrada!\n");
            return;
        }

        System.out.print(
            """
             1 - Atualizar nome
             2 - Atualizar descrição
             3 - Atualizar prioridade
             4 - Não Atualizar
           
             Digite sua opção:\s"""
        );
        String option = scanner.nextLine().trim();

        String parameter = null;

        switch (option) {
            case "1" -> parameter = "name";
            case "2" -> parameter = "description";
            case "3" -> parameter = "priority";
            case "4" -> {return;}
        }

        String sql = String.format("UPDATE todo SET %s = ? WHERE id = ?", parameter);

        System.out.print("Digite o novo valor: ");
        String value = scanner.nextLine().trim();

        if (value.isEmpty()) {
            System.out.println("Valor digitado é invalido tente novamente!\n");
            return;
        }

        try (
            Connection conn = storage.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            if (option.equals("3")) {
                int intValue;

                try {
                    intValue = Integer.parseInt(value);
                } catch (Exception e) {
                    System.out.println("Valor digitado não é valido, tente novamente!\n");
                    return;
                }

                stmt.setInt(1, intValue);
            } else {
                stmt.setString(1, value);
            }

            stmt.setString(2, taskId);

            stmt.executeUpdate();
            System.out.println("Valor atualizado com sucesso!\n");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private boolean TaskExistsById(String taskId) {
        String sql = "SELECT COUNT(*) FROM todo WHERE id = ?";

        try (
            Connection conn = storage.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, taskId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking if ID exists: " + e.getMessage());
        }
        return false;
    }
}
