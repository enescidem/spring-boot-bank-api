package com.enescidem.service;

import com.enescidem.dto.DtoLogin;
import com.enescidem.dto.DtoLoginIU;

public interface IAuthService {
	public DtoLogin login(DtoLoginIU dtoLoginIU);
}
