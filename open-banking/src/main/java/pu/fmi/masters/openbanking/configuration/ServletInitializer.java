package pu.fmi.masters.openbanking.configuration;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import pu.fmi.masters.openbanking.OpenBankingApplication;

/**
 * This class enables running the application from a .war file.
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(OpenBankingApplication.class);
	}

}
