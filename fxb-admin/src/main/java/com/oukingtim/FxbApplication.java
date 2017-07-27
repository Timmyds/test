package com.oukingtim;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.core.env.Environment;

import com.oukingtim.config.LoggingAspect;
@Slf4j
@SpringBootApplication
public class FxbApplication extends SpringBootServletInitializer{
	private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

	 @Override
	 protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)  {
	        return builder.sources(FxbApplication.class);
	  }

	public static void main(String[] args) {
		Environment env = SpringApplication.run(FxbApplication.class, args).getEnvironment();
		log.info("\n----------------------------------------------------------\n\t" +
						"Application '{}' is running! Access URLs:\n\t" +
						"Local: \t\thttp://localhost:{}\n----------------------------------------------------------",
				env.getProperty("spring.application.name"),
				env.getProperty("server.port"));
	}
}
