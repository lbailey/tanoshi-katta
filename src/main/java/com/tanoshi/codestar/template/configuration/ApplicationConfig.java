package com.tanoshi.codestar.template.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.tanoshi.codestar.template.controller.TanoshiController;

/**
 * Spring configuration for sample application.
 */
@Configuration
@ComponentScan({"com.aws.codestar.template.configuration"})
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

	/**
	 * Retrieved from properties file.
	 */
	@Value("${Tanoshi.SiteName}")
	private String siteName;
	
	@Value("${Tanoshi.API_KEY}")
	private String apiKey;

	@Bean
	public TanoshiController helloWorld() {
			return new TanoshiController(this.siteName, this.apiKey);
	}

	/**
	 * Required to inject properties using the 'Value' annotation.
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
			return new PropertySourcesPlaceholderConfigurer();
	}

}
