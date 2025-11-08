package org.trinogin.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class UserEntity {

    @Getter
    private final String username;

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

    public UserEntity(String username, String email, String displayName, Long userId, String password, String role) {
        this.username = username;
        this.email = email;
        this.displayName = displayName;
        this.userId = userId;
        this.password = password;
        this.role = role;
    }

    public String getAuthorities() {
        return role;
    }
}