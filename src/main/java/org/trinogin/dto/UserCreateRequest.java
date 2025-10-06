package org.trinogin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {
    private  String userName;
    private  String email;
    private  String displayName;
    private  String password;
}
