package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GUIApp extends JFrame {
    private final int WIDTH = 1000;
    private final int HEIGHT = 600;
    private JButton createTaskButton, deleteTaskButton, OpenTaskButton;
    private JTable tasksTable;

    public GUIApp() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Task Manager");
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);


        this.createTaskButton = new JButton("Add Task");
        createTaskButton.setBounds(15, 10, 200, 30);
        createTaskButton.setFocusable(false);

        this.deleteTaskButton = new JButton("Delete Task");
        deleteTaskButton.setBounds(15, 60, 200, 30);
        deleteTaskButton.setEnabled(false);
        deleteTaskButton.setFocusable(false);

        this.OpenTaskButton = new JButton("Select Task");
        OpenTaskButton.setBounds(15, 110, 200, 30);
        OpenTaskButton.setEnabled(false);
        OpenTaskButton.setFocusable(false);

        this.tasksTable = new JTable();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("id");
        model.addColumn("name");
        model.addColumn("description");
        model.addColumn("priority");

        tasksTable.setModel(model);
        tasksTable.setDefaultEditor(Object.class, null);
        tasksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollWithTable = new JScrollPane(tasksTable);
        scrollWithTable.setBounds(230, 10, 745, 540);

        this.setVisible(true);

        this.add(scrollWithTable);
        this.add(createTaskButton);
        this.add(deleteTaskButton);
        this.add(OpenTaskButton);

        createTaskButton.addActionListener(e -> {
            System.out.println("Adding Row!");
            new CreateTaskView();

            Object[] mockData = new Object[]{"mockId", "MockName", "mocksexo", 23};

            getTableModel().addRow(mockData);
        });

        tasksTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = tasksTable.getSelectedRow();

            if (selectedRow != -1) {
                Object rowData = getTableModel().getDataVector().get(selectedRow);
                System.out.println("Selected Row Data: " + rowData.toString());

                if (!OpenTaskButton.isEnabled()) {
                    OpenTaskButton.setEnabled(true);
                    deleteTaskButton.setEnabled(true);
                }

            }
        });

        deleteTaskButton.addActionListener(e -> {
            int selectedRow = tasksTable.getSelectedRow();

            if (selectedRow != -1) {
                getTableModel().removeRow(selectedRow);

                OpenTaskButton.setEnabled(false);
                deleteTaskButton.setEnabled(false);

                System.out.println("Task deleted!");
                JOptionPane.showMessageDialog(this, "Row deleted successfully!");
            }
        });
    }

    public DefaultTableModel getTableModel() {
        if (tasksTable != null) {
            return (DefaultTableModel) this.tasksTable.getModel();
        }
        return null;
    }

    public static void main(String[] args) {
        new GUIApp();
    }
}
