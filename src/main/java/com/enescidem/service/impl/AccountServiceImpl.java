package com.enescidem.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enescidem.dto.DtoAccount;
import com.enescidem.dto.DtoAccountIU;
import com.enescidem.entity.Account;
import com.enescidem.exception.BaseException;
import com.enescidem.exception.ErrorMessage;
import com.enescidem.exception.MessageType;
import com.enescidem.repository.AccountRepository;
import com.enescidem.service.IAccountService;

@Service
@Transactional
public class AccountServiceImpl implements IAccountService{
	
	
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    
    public AccountServiceImpl(AccountRepository accountRepository,
        PasswordEncoder passwordEncoder) {
		this.accountRepository = accountRepository;
		this.passwordEncoder = passwordEncoder;
    }

    private DtoAccount mapToDtoAccount(Account account) {
        DtoAccount dto = new DtoAccount();
        dto.setId(account.getId());
        dto.setUsername(account.getUsername());
        dto.setEmail(account.getEmail());
        dto.setRole(account.getRole());
        dto.setBalance(account.getBalance());
        return dto;
    }
    
    
    @Override
    public DtoAccount createAccount(DtoAccountIU dtoAccountIU) {
        if (accountRepository.existsByUsername(dtoAccountIU.getUsername())) {
        	throw new BaseException(
                    new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Username already exists")
                );
        }
        if (accountRepository.existsByEmail(dtoAccountIU.getEmail())) {
        	throw new BaseException(
                    new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Email already exists")
                );        
        }

        Account account = Account.builder()
                .username(dtoAccountIU.getUsername())
                .password(passwordEncoder.encode(dtoAccountIU.getPassword()))
                .email(dtoAccountIU.getEmail())
                .role(dtoAccountIU.getRole() != null ? dtoAccountIU.getRole() : "USER")
                .balance(0.0)
                .build();

        Account saved = accountRepository.save(account);
        return mapToDtoAccount(saved);
    }

    @Override
    public DtoAccount getAccountByUsername(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, username)
                ));
        return mapToDtoAccount(account);
    }

    @Override
    public void depositByUsername(String username, Double amount) {
    	if (amount <= 0) {
            throw new BaseException(
                new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Deposit amount must be positive")
            );
        }
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, username)
                ));
        
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    @Override
    public void withdrawByUsername(String username, Double amount) {
    	if (amount <= 0) {
            throw new BaseException(
                new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Withdraw amount must be positive")
            );
        }
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, username)
                ));

        if (account.getBalance() < amount) {
        	throw new BaseException(
                    new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Insufficient balance")
                );
        }
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }

    @Override
    public void transferByUsername(String fromUsername, Long toAccountId, Double amount) {
    	if (amount <= 0) {
            throw new BaseException(
                new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Transfer amount must be positive")
            );
        }
    	
        Account fromAccount = accountRepository.findByUsername(fromUsername)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, fromUsername)
                ));


        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, toAccountId.toString())
                ));

        if (fromAccount.getBalance() < amount) {
        	throw new BaseException(
                    new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Insufficient balance")
                );
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }
}

