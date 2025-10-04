package org.trinogin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class UserEntity {

    @Getter
    private final String userName;

    @Getter
    private final String email;

    @Getter
    private final String displayName;

    @Getter
    @Setter
    private Long userId;

    @Getter
    @Setter
    @ToString.Exclude
    private String password;

    @Getter
    private final String role;

    public UserEntity(String userName, String email, String displayName, Long userId, String password, String role) {
        this.userName = userName;
        this.email = email;
        this.displayName = displayName;
        this.userId = userId;
        this.password = password;
        this.role = role;
    }

    public UserEntity (User user) {
        this.userName = user.getUsername();
        this.email = user.getEmail();
        this.displayName = user.getDisplayName();
        this.userId = Long.valueOf(user.getUserId());
        this.password = user.getPassword();
        this.role = user.getAuthorities();
    }

    public String getAuthorities() {
        return role;
    }
}