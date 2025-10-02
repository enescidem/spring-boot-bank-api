package com.enescidem.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enescidem.dto.DtoLogin;
import com.enescidem.dto.DtoLoginIU;
import com.enescidem.service.IAuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public DtoLogin login(@RequestBody DtoLoginIU dtoLoginIU) {
        return authService.login(dtoLoginIU);
    }
}