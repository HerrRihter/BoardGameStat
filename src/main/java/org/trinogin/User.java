package org.trinogin;

public class User {
    private final String userName;
    private final String email;
    private final String displayName;
    private final String userId;

    public User(String userName, String email, String displayName, String userId) {
        this.userName = userName;
        this.email = email;
        this.displayName = displayName;
        this.userId = userId;
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
}