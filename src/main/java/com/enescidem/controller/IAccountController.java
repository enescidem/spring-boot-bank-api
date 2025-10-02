package com.enescidem.controller;

import org.springframework.http.ResponseEntity;

import com.enescidem.dto.DtoAccount;
import com.enescidem.dto.DtoAccountIU;

public interface IAccountController {
	
    ResponseEntity<DtoAccount> createAccount(DtoAccountIU dtoAccountIU);

    ResponseEntity<DtoAccount> getMyAccount(String authHeader);

    ResponseEntity<String> deposit(String authHeader, Double amount);

    ResponseEntity<String> withdraw(String authHeader, Double amount);

    ResponseEntity<String> transfer(String authHeader, Long toAccountId, Double amount);
	
}
