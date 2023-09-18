package com.UdyWeber.TaskManager;

import java.util.Scanner;

import Structs.Board;
import Db.Database;


public class App {
    public static void main(String[] args) {
        Database db = new Database();
        db.createSchema();

        Board myBoard = new Board();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(
                """
                ============== TASK MANAGER ==============
                1 - LISTAR TASKS
                2 - ABRIR TASK
                3 - ADICIONAR TASK
                4 - REMOVER TASK
                5 - ATUALIZAR TASK
                6 - FECHAR TASK MANAGER

                Digite sua opção:\s"""
            );

            switch (scanner.nextLine().trim()) {
                case "1" -> myBoard.listTasks();
                case "2" -> myBoard.getTask(scanner);
                case "3" -> myBoard.addTask(scanner);
                case "4" -> myBoard.deleteTask(scanner);
                case "5" -> myBoard.updateTask(scanner);
                case "6" -> {
                    System.out.println("Encerrando a execução!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Opção invalida, tente novamente!\n");
            }
        }
    }
}
