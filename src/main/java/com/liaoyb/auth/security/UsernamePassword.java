package com.liaoyb.auth.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsernamePassword {
    private String username;
    private String password;
}
