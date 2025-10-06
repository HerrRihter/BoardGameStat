package org.trinogin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    private String displayName;
    private String email;
    private String userName;
}
