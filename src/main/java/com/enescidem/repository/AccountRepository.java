package com.enescidem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enescidem.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
	
    Optional<Account> findByUsername(String username);

    Optional<Account> findByEmail(String email);
    
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
