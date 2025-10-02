package com.enescidem.obss_bank_demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.enescidem.dto.DtoLogin;
import com.enescidem.dto.DtoLoginIU;
import com.enescidem.service.IAuthService;
import com.enescidem.starter.ObssBankDemoStarter;

@SpringBootTest(classes = {ObssBankDemoStarter.class})
class ObssBankDemoApplicationTests {
	
	@Autowired
	private IAuthService authService;
	
	@Test
	public void testLoginMethod() throws Exception {
			DtoLoginIU testDtoLoginIU = new DtoLoginIU();
			testDtoLoginIU.setUsername("enes");
			testDtoLoginIU.setPassword("1234");
			
			DtoLogin login = authService.login(testDtoLoginIU);
			if(login !=null) {
				System.out.println("Token : "+login.getToken());
			}
	}
	
}
