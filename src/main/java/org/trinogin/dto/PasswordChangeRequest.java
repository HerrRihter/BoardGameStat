package org.trinogin.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@RequiredArgsConstructor
@Getter
@Setter
public class PasswordChangeRequest {

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 6, max = 100)
    private String newPassword;
}