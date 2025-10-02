package com.enescidem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoAccountIU {
    private String username;
    private String password;
    private String email;
    private String role;
}
