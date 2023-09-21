package UI;

import javax.swing.*;

public class CreateTaskView extends JFrame {
    CreateTaskView() {
        this.setSize(500, 300);
        this.setTitle("Task Manager");
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);

        this.setVisible(true);

        JLabel x = new JLabel("Insert task name: ");
        x.setBounds(15, 10, 200, 20);

        JTextField k = new JTextField();

        JLabel y = new JLabel("Insert task priority: ");
        y.setBounds(15, 40, 200, 20);
        JLabel z = new JLabel("Insert task description: ");
        z.setBounds(15, 70, 200, 20);

        this.add(x);
        this.add(y);
        this.add(z);
    }
}
