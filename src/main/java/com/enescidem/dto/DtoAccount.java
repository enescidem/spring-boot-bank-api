package com.enescidem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoAccount {
    private Long id;
    private String username;
    private String email;
    private String role;
    private Double balance;
}
