package UI;

import Structs.Todo;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CreateTaskView extends JFrame {
    CreateTaskView(GUIApp rootFrame) {
        this.setSize(500, 300);
        this.setTitle("Create Task Window");
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                rootFrame.setEnabled(true);
                super.windowClosing(e);
            }
        });

        final int labelsXAlignment = 15;
        final int fieldsXAlignment = 155;

        JLabel taskNameLabel = new JLabel("Insert task name: ");
        taskNameLabel.setBounds(labelsXAlignment, 10, 200, 20);

        JTextField taskNameField = new JTextField();
        taskNameField.setBounds(fieldsXAlignment, 12, 200, 20);

        JLabel taskPriorityLabel = new JLabel("Insert task priority: ");
        taskPriorityLabel.setBounds(labelsXAlignment, 40, 200, 20);

        JTextField taskPriorityField = new JTextField();
        taskPriorityField.setBounds(fieldsXAlignment, 42, 200, 20);

        JLabel taskDescriptionLabel = new JLabel("Insert task description: ");
        taskDescriptionLabel.setBounds(labelsXAlignment, 70, 200, 20);

        JTextArea taskDescriptionTextArea = new JTextArea();
        taskDescriptionTextArea.setBounds(fieldsXAlignment, 72, 320, 165);
        taskDescriptionTextArea.setToolTipText("Insert the task description");

        JButton saveButton = new JButton("Save");
        saveButton.setFocusable(false);
        saveButton.setEnabled(false);
        saveButton.setBounds(labelsXAlignment, 180, 110,20);

        saveButton.addActionListener(e -> {
            String taskName = taskNameField.getText();
            String taskDescription = taskDescriptionTextArea.getText();
            int taskPriority;

            try {
                taskPriority = Integer.parseInt(taskPriorityField.getText().trim());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Priority must be a number!!");
                return;
            }

            Todo todo = new Todo(taskName, taskDescription, taskPriority);

            rootFrame.getTableModel().addRow(todo.getAsTableRow());
            rootFrame.setEnabled(true);
            this.dispose();
        });


        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFocusable(false);
        cancelButton.setBounds(labelsXAlignment, 218, 110, 20);

        cancelButton.addActionListener(e -> {
            rootFrame.setEnabled(true);
            this.dispose();
        });

        DocumentListener fieldsListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkFilled();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkFilled();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // No need to be implemented
            }

            private void checkFilled() {
                boolean isFulfilled = !taskNameField.getText().trim().isEmpty() &&
                        !taskPriorityField.getText().trim().isEmpty() &&
                        !taskDescriptionTextArea.getText().trim().isEmpty();
                saveButton.setEnabled(isFulfilled);
            }

        };

        taskNameField.getDocument().addDocumentListener(fieldsListener);
        taskPriorityField.getDocument().addDocumentListener(fieldsListener);
        taskDescriptionTextArea.getDocument().addDocumentListener(fieldsListener);


        this.add(taskNameLabel);
        this.add(taskNameField);
        this.add(taskPriorityLabel);
        this.add(taskPriorityField);
        this.add(taskDescriptionLabel);
        this.add(taskDescriptionTextArea);
        this.add(saveButton);
        this.add(cancelButton);

        this.setVisible(true);
    }
}
