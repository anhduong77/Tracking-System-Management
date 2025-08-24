package TrackingManager;

import javax.swing.*;
import java.awt.*;

public class UserGUI extends JFrame {
    private TrackingSystem trackingSystem;
    private JTextField searchField;
    private JTextArea resultArea;

    public UserGUI() {
        trackingSystem = new TrackingSystem();
        setTitle("User Tracking System");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        searchPanel.add(new JLabel("Enter Order ID:"));
        searchField = new JTextField();
        searchPanel.add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchOrder());
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }

    private void searchOrder() {
        String orderID = searchField.getText().trim();
        if (orderID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Order ID!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Order order = trackingSystem.searchOrder(orderID);
        if (order != null) {
            resultArea.setText("Order ID: " + order.orderID + "\n"
                    + "Destination: " + order.destinationAddress + "\n"
                    + "Sending Address: " + order.sendingAddress + "\n"
                    + "Status: " + (order.delivered ? "Delivered" : "Delivering"));
        } else {
            resultArea.setText("Order not found.");
        }
    }
}
