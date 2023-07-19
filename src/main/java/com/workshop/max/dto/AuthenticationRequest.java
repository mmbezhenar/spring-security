package com.workshop.max.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
public record AuthenticationRequest(String email, String password) {
}
