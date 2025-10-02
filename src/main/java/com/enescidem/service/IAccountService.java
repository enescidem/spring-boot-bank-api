package com.enescidem.service;

import com.enescidem.dto.DtoAccount;
import com.enescidem.dto.DtoAccountIU;

public interface IAccountService {
	
	public DtoAccount createAccount(DtoAccountIU dtoAccountIU);
	
	public DtoAccount getAccountByUsername(String username);
	
	public void depositByUsername(String username, Double amount);
	
	public void withdrawByUsername(String username, Double amount);
	
	public void transferByUsername(String fromUsername, Long toAccountId, Double amount);
	
}
