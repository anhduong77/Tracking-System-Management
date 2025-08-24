package TrackingManager;

public class Account {
    protected String username;
    protected String password;
    protected String role; // admin or user

    public Account(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}

class Admin extends Account {
    public Admin(String username, String password) {
        super(username, password, "admin");
    }
}

class User extends Account {
    public User(String username, String password) {
        super(username, password, "user");
    }
}
