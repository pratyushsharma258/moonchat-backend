package com.pratyushsharma258.moonchatbackend.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotNull(message = "Username is required.")
    private String username;

    @NotNull(message = "Password is required.")
    private String password;
}
