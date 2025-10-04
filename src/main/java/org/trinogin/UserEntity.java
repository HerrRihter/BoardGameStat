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
    @ToString.Exclude
    private final String password;

    private final String role;

    public UserEntity(String userName, String email, String displayName, Long userId, String password, String role) {
        this.userName = userName;
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