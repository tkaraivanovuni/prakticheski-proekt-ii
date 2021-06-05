package pu.fmi.masters.openbanking;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pu.fmi.masters.openbanking.controller.LoginController;

/**
 * This class tests if the {@link LoginController} is created properly.
 */
@SpringBootTest
public class LoginControllerSmokeTest {
	
	@Autowired
	private LoginController loginController;
	
	/**
	 * This method tests if the login controller is not null.
	 * @throws Exception
	 */
	@Test
	public void contextLoads() throws Exception {
		assertThat(loginController).isNotNull();
	}

}
