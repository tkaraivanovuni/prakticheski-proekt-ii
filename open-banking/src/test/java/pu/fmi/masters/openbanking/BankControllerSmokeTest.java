package pu.fmi.masters.openbanking;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pu.fmi.masters.openbanking.controller.BankController;

/**
 * This class tests if the {@link BankController} is created properly.
 */
@SpringBootTest
public class BankControllerSmokeTest {
	
	@Autowired
	private BankController bankController;
	
	@Test
	public void contextLoads() throws Exception {
		assertThat(bankController).isNotNull();
	}

}
