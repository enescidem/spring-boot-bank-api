package com.enescidem.controller.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enescidem.controller.IAccountController;
import com.enescidem.dto.DtoAccount;
import com.enescidem.dto.DtoAccountIU;
import com.enescidem.security.JwtUtil;
import com.enescidem.service.IAccountService;

@RestController
@RequestMapping("/accounts")
public class AccountControllerImpl implements IAccountController {

    private final IAccountService accountService;
    private final JwtUtil jwtUtil;

    public AccountControllerImpl(IAccountService accountService, JwtUtil jwtUtil) {
        this.accountService = accountService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<DtoAccount> createAccount(@RequestBody DtoAccountIU dtoAccountIU) {
        return ResponseEntity.ok(accountService.createAccount(dtoAccountIU));
    }

    @Override
    @GetMapping("/me")
    public ResponseEntity<DtoAccount> getMyAccount(@RequestHeader("Authorization") String authHeader) {
        String username = jwtUtil.extractUsernameFromHeader(authHeader);
        return ResponseEntity.ok(accountService.getAccountByUsername(username));
    }

    @Override
    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestHeader("Authorization") String authHeader, 
                                          @RequestParam Double amount) {
        String username = jwtUtil.extractUsernameFromHeader(authHeader);
        accountService.depositByUsername(username, amount);
        return ResponseEntity.ok("Deposit successful");
    }

    @Override
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestHeader("Authorization") String authHeader,
                                           @RequestParam Double amount) {
        String username = jwtUtil.extractUsernameFromHeader(authHeader);
        accountService.withdrawByUsername(username, amount);
        return ResponseEntity.ok("Withdraw successful");
    }

    @Override
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestHeader("Authorization") String authHeader,
                                           @RequestParam Long toAccountId, 
                                           @RequestParam Double amount) {
        String username = jwtUtil.extractUsernameFromHeader(authHeader);
        accountService.transferByUsername(username, toAccountId, amount);
        return ResponseEntity.ok("Transfer successful");
    }
}
