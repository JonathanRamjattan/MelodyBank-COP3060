package com.midihub.melodyhub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {
    private Long id;
    private String username;
    private String email;
    private boolean creator;
    private String message;
}