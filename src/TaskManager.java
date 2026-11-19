
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
/**
 * Main class responsible for the Task Management System GUI.
 *
 * @author Fatima Zulfiqar
 */
public class TaskManager {

    private JFrame frame;
    private JTable taskTable;
    private DefaultTableModel tableModel;
    private TaskManagerService service;
    private JPanel tableContainer;
    private JLabel totalLabel;
    private JLabel pendingLabel;
    private JLabel progressLabel;
    private JLabel completedLabel;

    private final Color NAVY = new Color(25, 45, 85);
    private final Color BLUE = new Color(55, 110, 220);
    private final Color BACKGROUND = new Color(242, 246, 252);
    private final Color WHITE = Color.WHITE;
/** Initializes the Task Management System interface.
 */
    public TaskManager() {
        service = new TaskManagerService();

        frame = new JFrame("Task Management Dashboard");
        frame.setSize(1050, 680);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(BACKGROUND);
        frame.setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NAVY);
        header.setBorder(BorderFactory.createEmptyBorder(22, 30, 22, 30));

        JLabel heading = new JLabel("Task Management System");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 27));
        heading.setForeground(WHITE);

        JLabel subtitle = new JLabel("Organize your work and track your progress");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(new Color(205, 218, 240));

        JPanel headerText = new JPanel(new GridLayout(2, 1, 0, 5));
        headerText.setOpaque(false);
        headerText.add(heading);
        headerText.add(subtitle);

        header.add(headerText, BorderLayout.WEST);

        JLabel logo = new JLabel("MY DASHBOARD");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logo.setForeground(WHITE);
        header.add(logo, BorderLayout.EAST);

        frame.add(header, BorderLayout.NORTH);

        // Main content
        JPanel content = new JPanel(new BorderLayout(20, 20));
        content.setBackground(BACKGROUND);
        content.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // Summary cards
        JPanel cards = new JPanel(new GridLayout(1, 4, 15, 15));
        cards.setOpaque(false);

        totalLabel = createCard(cards, "TOTAL TASKS", BLUE);
        pendingLabel = createCard(cards, "PENDING", new Color(220, 155, 35));
        progressLabel = createCard(cards, "IN PROGRESS", new Color(120, 85, 205));
        completedLabel = createCard(cards, "COMPLETED", new Color(35, 155, 105));

        JPanel topSection = new JPanel(new BorderLayout(0, 15));
        topSection.setOpaque(false);

        JLabel sectionTitle = new JLabel("Overview");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        sectionTitle.setForeground(NAVY);

        topSection.add(sectionTitle, BorderLayout.NORTH);
        topSection.add(cards, BorderLayout.CENTER);

        content.add(topSection, BorderLayout.NORTH);

        // Task table
        String[] columns = {"ID", "Task Title", "Description", "Status"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        taskTable = new JTable(tableModel);
        taskTable.setRowHeight(38);
        taskTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        taskTable.setSelectionBackground(new Color(215, 229, 250));
        taskTable.setSelectionForeground(NAVY);
        taskTable.setShowVerticalLines(false);
        taskTable.setGridColor(new Color(230, 235, 243));
        taskTable.setFillsViewportHeight(true);

        JTableHeader tableHeader = taskTable.getTableHeader();
        tableHeader.setBackground(NAVY);
        tableHeader.setForeground(WHITE);
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableHeader.setPreferredSize(new Dimension(0, 42));

        taskTable.getColumnModel().getColumn(0).setPreferredWidth(45);
        taskTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        taskTable.getColumnModel().getColumn(2).setPreferredWidth(430);
        taskTable.getColumnModel().getColumn(3).setPreferredWidth(130);

        taskTable.getColumnModel().getColumn(3).setCellRenderer(
            new StatusRenderer()
        );

        JScrollPane scrollPane = new JScrollPane(taskTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(
            new Color(225, 231, 240)
        ));

        // Empty table message
        JLabel emptyLabel = new JLabel(
            "<html><center>No tasks available.<br>Click + Add Task to get started!</center></html>",
            SwingConstants.CENTER
        );

        emptyLabel.setFont(new Font("Segoe UI", Font.BOLD, 17));
        emptyLabel.setForeground(new Color(140, 150, 165));
        emptyLabel.setOpaque(true);
        emptyLabel.setBackground(WHITE);

        // Table container
        tableContainer = new JPanel(new CardLayout());
        tableContainer.add(scrollPane, "TABLE");
        tableContainer.add(emptyLabel, "EMPTY");

        JPanel tablePanel = new JPanel(new BorderLayout(0, 12));
        tablePanel.setOpaque(false);

        JLabel taskHeading = new JLabel("Your Tasks");
        taskHeading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        taskHeading.setForeground(NAVY);

        tablePanel.add(taskHeading, BorderLayout.NORTH);
        tablePanel.add(tableContainer, BorderLayout.CENTER);
        content.add(tablePanel, BorderLayout.CENTER);

        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 5));
        buttonPanel.setOpaque(false);

        JButton addButton = createButton(" + Add Task", BLUE);
        JButton updateButton = createButton("Update Task", NAVY);
        JButton deleteButton = createButton("Delete Task", new Color(205, 65, 75));
        JButton statusButton = createButton("Change Status", new Color(120, 85, 205));

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(statusButton);

        content.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(content, BorderLayout.CENTER);

        addButton.addActionListener(e -> addTask());
        updateButton.addActionListener(e -> updateTask());
        deleteButton.addActionListener(e -> deleteTask());
        statusButton.addActionListener(e -> changeStatus());

        refreshTable();
        frame.setVisible(true);
    }

    private JLabel createCard(JPanel panel, String title, Color color) {
        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 0, 4, color),
            BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLabel.setForeground(new Color(105, 115, 130));

        JLabel valueLabel = new JLabel("0");
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 29));
        valueLabel.setForeground(color);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        panel.add(card);
        return valueLabel;
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(145, 40));
        return button;
    }

    private void refreshTable() {
        tableModel.setRowCount(0);

        int pending = 0;
        int progress = 0;
        int completed = 0;

        for (Task task : service.getAllTasks()) {
            tableModel.addRow(new Object[]{
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus()
            });

            switch (task.getStatus()) {
                case "Pending": pending++; break;
                case "In Progress": progress++; break;
                case "Completed": completed++; break;
            }
        }

        totalLabel.setText(String.valueOf(service.getAllTasks().size()));
        pendingLabel.setText(String.valueOf(pending));
        progressLabel.setText(String.valueOf(progress));
        completedLabel.setText(String.valueOf(completed));

        CardLayout layout = (CardLayout) tableContainer.getLayout();

        if (tableModel.getRowCount() == 0) {
            layout.show(tableContainer, "EMPTY");
        } else {
            layout.show(tableContainer, "TABLE");
        }
    }

    private void addTask() {
        JTextField titleField = new JTextField();
        JTextArea descriptionArea = new JTextArea(4, 20);

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.add(new JLabel("Task Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(descriptionArea));

        int result = JOptionPane.showConfirmDialog(
            frame, panel, "Add New Task", JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                service.addTask(titleField.getText(), descriptionArea.getText());
                refreshTable();
                JOptionPane.showMessageDialog(frame, "Task added successfully!");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                    frame, ex.getMessage(), "Validation Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void updateTask() {
        int row = taskTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a task.");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        JTextField title = new JTextField((String) tableModel.getValueAt(row, 1));
        JTextArea description = new JTextArea(
            (String) tableModel.getValueAt(row, 2), 4, 20
        );

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.add(new JLabel("Task Title:"));
        panel.add(title);
        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(description));

        int result = JOptionPane.showConfirmDialog(
            frame, panel, "Update Task", JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                if (service.updateTask(id, title.getText(), description.getText())) {
                    refreshTable();
                    JOptionPane.showMessageDialog(frame, "Task updated successfully!");
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        }
    }

    private void deleteTask() {
        int row = taskTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a task.");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(
            frame, "Are you sure you want to delete this task?",
            "Confirm Delete", JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            service.deleteTask(id);
            refreshTable();
            JOptionPane.showMessageDialog(frame, "Task deleted successfully!");
        }
    }

    private void changeStatus() {
        int row = taskTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a task.");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);

        String[] statuses = {"Pending", "In Progress", "Completed"};

        String selected = (String) JOptionPane.showInputDialog(
            frame, "Select Task Status:", "Change Status",
            JOptionPane.QUESTION_MESSAGE, null, statuses,
            tableModel.getValueAt(row, 3)
        );

        if (selected != null) {
            service.updateStatus(id, selected);
            refreshTable();
            JOptionPane.showMessageDialog(frame, "Status updated successfully!");
        }
    }

    private class StatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
            JTable table, Object value, boolean selected,
            boolean focus, int row, int column
        ) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(
                table, value, selected, focus, row, column
            );

            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));
            label.setOpaque(true);

            if (selected) {
                label.setBackground(table.getSelectionBackground());
                label.setForeground(NAVY);
            } else {
                label.setBackground(WHITE);

                if ("Completed".equals(value)) {
                    label.setForeground(new Color(25, 145, 90));
                } else if ("In Progress".equals(value)) {
                    label.setForeground(new Color(120, 85, 205));
                } else {
                    label.setForeground(new Color(210, 140, 25));
                }
            }

            return label;
        }
    }
/** Starts the Task Management System application.
 * @param args command-line arguments
 */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TaskManager());
    }
}
