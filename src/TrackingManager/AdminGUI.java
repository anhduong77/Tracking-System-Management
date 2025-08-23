package TrackingManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

public class AdminGUI extends JFrame {
    private TrackingSystem TrackingSystem;
    private JTextField orderIDField, destinationAddressField, sendingAddressField, searchField;
    private JTable orderTable;
    private DefaultTableModel tableModel;

    public AdminGUI() {
        TrackingSystem = new TrackingSystem();
        // TrackingSystem.addOrder("012", "Introduction to Algorithms", "Thomas H. Cormen");
        // TrackingSystem.addOrder("123", "Cracking the Coding Interview", "Gayle Laakmann McDowell");
        // TrackingSystem.addOrder("345", "Design Patterns", "Erich Gamma");
        // TrackingSystem.addOrder("098", "Clean Code", "Robert C. Martin");
        // TrackingSystem.addOrder("901", "Head First Java", "Kathy Sierra");
        // TrackingSystem.addOrder("234", "Effective Java", "Joshua Bloch");
        // TrackingSystem.addOrder("557", "Java Concurrency in Practice", "Brian Goetz");
        // TrackingSystem.addOrder("840", "Java: The Complete Reference", "Herbert Schildt");
        // TrackingSystem.addOrder("103", "Thinking in Java", "Bruce Eckel");
        // TrackingSystem.addOrder("456", "Java: A Beginner's Guide", "Herbert Schildt");
        // TrackingSystem.addOrder("789", "Java: The Good Parts", "Douglas Crockford");
        // TrackingSystem.addOrder("082", "How to Cook", "Jenny Dale");

        initializeUI();
        displayOrders();
    }

    private void initializeUI() {
        setTitle("TrackingSystem Management System");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Tracking System"));

        inputPanel.add(new JLabel("Order ID:"));
        orderIDField = new JTextField();
        inputPanel.add(orderIDField);

        inputPanel.add(new JLabel("Destination Address:"));
        destinationAddressField = new JTextField();
        inputPanel.add(destinationAddressField);

        inputPanel.add(new JLabel("Sending Address:"));
        sendingAddressField = new JTextField();
        inputPanel.add(sendingAddressField);

        JButton addButton = new JButton("Add Order");
        addButton.addActionListener(e -> addOrder());
        inputPanel.add(addButton);

        JButton updateButton = new JButton("Update Order");
        updateButton.addActionListener(e -> updateOrder());
        inputPanel.add(updateButton);

        add(inputPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Order ID", "Destination Address", "Sending Address", "Delivered"}, 0);
        orderTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(orderTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("TrackingSystem Orders"));
        add(scrollPane, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        actionPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

        searchField = new JTextField();
        actionPanel.add(new JLabel("Enter the Order ID:"));
        actionPanel.add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchOrder());
        actionPanel.add(searchButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteOrder());
        actionPanel.add(deleteButton);

        JButton borrowButton = new JButton("Delivered");
        borrowButton.addActionListener(e -> borrowOrder());
        actionPanel.add(borrowButton);

        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(e -> returnOrder());
        actionPanel.add(returnButton);

        JButton saveButton = new JButton("Save to File");
        saveButton.addActionListener(e -> saveToFile());
        actionPanel.add(saveButton);

        JButton loadButton = new JButton("Load from File");
        loadButton.addActionListener(e -> loadFromFile());
        actionPanel.add(loadButton);

        JButton countButton = new JButton("Count Orders");
        countButton.addActionListener(e -> countOrders());
        actionPanel.add(countButton);

        add(actionPanel, BorderLayout.SOUTH);
    }

    private void addOrder() {
        String orderID = orderIDField.getText().trim();
        String destinationAddress = destinationAddressField.getText().trim();
        String sendingAddress = sendingAddressField.getText().trim();

        if (orderID.isEmpty() || destinationAddress.isEmpty() || sendingAddress.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        TrackingSystem.addOrder(orderID, destinationAddress, sendingAddress);
        displayOrders();
        clearFields();
        JOptionPane.showMessageDialog(this, "Order added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateOrder() {
        String isbn = orderIDField.getText().trim();
        String destinationAddress = destinationAddressField.getText().trim();
        String sendingAddress = sendingAddressField.getText().trim();

        if (isbn.isEmpty() || sendingAddress.isEmpty() || destinationAddress.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Order Order = TrackingSystem.searchOrder(isbn);
        if (Order != null) {
            Order.destinationAddress = destinationAddress;
            Order.sendingAddress = sendingAddress;
            displayOrders();
            clearFields();
            JOptionPane.showMessageDialog(this, "Order updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Order not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchOrder() {
        String orderID = searchField.getText().trim();
        if (orderID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Order ID to search.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Order Order = TrackingSystem.searchOrder(orderID);
        if (Order != null) {
            JOptionPane.showMessageDialog(this, "Order found:\n" + Order, "Search Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Order not found.", "Search Result", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteOrder() {
        String orderID = searchField.getText().trim();
        if (orderID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an order ID to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        TrackingSystem.deleteOrder(orderID);
        displayOrders();
        JOptionPane.showMessageDialog(this, "Order deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void borrowOrder() {
        String orderID = searchField.getText().trim();
        if (orderID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an order ID successfuly delivered.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (TrackingSystem.borrowOrder(orderID)) {
            displayOrders();
            JOptionPane.showMessageDialog(this, "Order delivered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Order not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void returnOrder() {
        String orderID = searchField.getText().trim();
        if (orderID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an order ID to return.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (TrackingSystem.returnOrder(orderID)) {
            displayOrders();
            JOptionPane.showMessageDialog(this, "Order returned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Order not found or not deliver.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveToFile() {
        try (FileWriter writer = new FileWriter("TrackingSystem.txt")) {
            TrackingSystem.displayOrdersInOrder(Order -> {
                try {
                    writer.write(Order + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            JOptionPane.showMessageDialog(this, "Trackings saved to file.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving to file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("TrackingSystem.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 4) {
                    String orderID = parts[0].split(": ")[1];
                    String destinationAddress = parts[1].split(": ")[1];
                    String sendingAddress = parts[2].split(": ")[1];
                    boolean delivered = parts[3].split(": ")[1].equals("Yes");
                    Order Order = new Order(orderID, destinationAddress, sendingAddress);
                    Order.delivered = delivered;
                    TrackingSystem.addOrder(Order.orderID, Order.destinationAddress, Order.sendingAddress);
                }
            }
            displayOrders();
            JOptionPane.showMessageDialog(this, "Trackings loaded from file.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading from file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void countOrders() {
        int count = TrackingSystem.countOrders();
        JOptionPane.showMessageDialog(this, "Total Orders in Tracking System: " + count, "Count Orders", JOptionPane.INFORMATION_MESSAGE);
    }

    private void displayOrders() {
        tableModel.setRowCount(0); // Clear existing rows
        TrackingSystem.displayOrdersInOrder(Order -> tableModel.addRow(new Object[]{Order.orderID, Order.destinationAddress, Order.sendingAddress, Order.delivered ? "Delivered" : "Delivering"}));
    }

    private void clearFields() {
        orderIDField.setText("");
        destinationAddressField.setText("");
        sendingAddressField.setText("");
        searchField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminGUI gui = new AdminGUI();
            gui.setVisible(true);
        });
    }
}
