package pu.fmi.masters.openbanking;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pu.fmi.masters.openbanking.controller.UserController;

/**
 * This class tests for whether the {@link UserController} is created properly.
 */
@SpringBootTest
public class UserControllerSmokeTest {
	
	@Autowired
	private UserController userController;
	
	@Test
	public void contextLoads() throws Exception {
		assertThat(userController).isNotNull();
	}

}
