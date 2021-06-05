package pu.fmi.masters.openbanking;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pu.fmi.masters.openbanking.controller.BankAccountController;

/**
 * This class tests if the {@link BankAccountController} is created properly.
 */
@SpringBootTest
public class BankAccountControllerSmokeTest {
	
	@Autowired
	private BankAccountController bankAccountController;
	
	@Test
	public void contextLoads() throws Exception {
		assertThat(bankAccountController).isNotNull();
	}

}
