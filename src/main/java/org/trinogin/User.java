package org.trinogin;

public class User {
    private final String userName;
    private final String email;
    private final String displayName;
    private final String userId;
    private final String password;
    private final String role;

    public User(String userName, String email, String displayName, String userId, String password, String role) {
        this.userName = userName;
        this.email = email;
        this.displayName = displayName;
        this.userId = userId;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthorities() {
        return role;
    }
}