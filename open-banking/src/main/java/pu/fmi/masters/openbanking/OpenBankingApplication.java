package pu.fmi.masters.openbanking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the starting class of the application.
 */
@SpringBootApplication(scanBasePackages = "pu.fmi.masters.openbanking")
public class OpenBankingApplication {

	/**
	 * This method runs the application.
	 * 
	 * @param args - the parameters defined in application.properties
	 */
	public static void main(String[] args) {
		SpringApplication.run(OpenBankingApplication.class, args);
	}
	
}
