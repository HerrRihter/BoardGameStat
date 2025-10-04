package org.trinogin.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class PasswordChangeRequest {

    private String username;

//    @NotBlank
//    @Size(min = 8)
    private String newPassword;
}