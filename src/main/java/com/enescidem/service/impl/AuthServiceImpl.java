package com.enescidem.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.enescidem.dto.DtoLogin;
import com.enescidem.dto.DtoLoginIU;
import com.enescidem.entity.Account;
import com.enescidem.exception.BaseException;
import com.enescidem.exception.ErrorMessage;
import com.enescidem.exception.MessageType;
import com.enescidem.repository.AccountRepository;
import com.enescidem.security.JwtUtil;
import com.enescidem.service.IAuthService;

@Service
public class AuthServiceImpl implements IAuthService{
	private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    public AuthServiceImpl(AccountRepository accountRepository,  PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    
    @Override
    public DtoLogin login(DtoLoginIU dtoLoginIU) {
        Account account = accountRepository.findByUsername(dtoLoginIU.getUsername())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Username not found")
                ));

        if (!passwordEncoder.matches(dtoLoginIU.getPassword(), account.getPassword())) {
        	throw new BaseException(
                    new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Incorrect password")
            );
        }

        String token = jwtUtil.generateToken(account.getUsername(), account.getRole());
        return new DtoLogin(token);
    }
}
