package TrackingManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private ArrayList<Account> accounts; // account list

    public LoginGUI() {
        setTitle("Login System");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // password
        accounts = new ArrayList<>();
        accounts.add(new Admin("admin", "admin123"));
        accounts.add(new User("user", "user123"));

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // space between components

        // username label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST; // right indent
        panel.add(new JLabel("Username:"), gbc);

        // username field
        usernameField = new JTextField(15); // 15 characters
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(usernameField, gbc);

        // password label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Password:"), gbc);

        // password field
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(passwordField, gbc);

        // login button
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(80, 25)); // resize button
        loginButton.addActionListener(e -> login());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        // exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(80, 25));
        exitButton.addActionListener(e -> System.exit(0));
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(exitButton, gbc);

        add(panel);
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        for (Account acc : accounts) {
            if (acc.getUsername().equals(username) && acc.getPassword().equals(password)) {
                JOptionPane.showMessageDialog(this, "Login successful as " + acc.getRole());
                this.dispose();

                if (acc.getRole().equals("admin")) {
                    SwingUtilities.invokeLater(() -> new AdminGUI().setVisible(true));
                } else {
                    SwingUtilities.invokeLater(() -> new UserGUI().setVisible(true));
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginGUI().setVisible(true));
    }
}
