package com.plusesb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class VeniceApplication {

	protected final static Logger logger = LoggerFactory.getLogger(VeniceApplication.class);
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(VeniceApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
		logger.info("VeniceApplication is success!");
	}

}
