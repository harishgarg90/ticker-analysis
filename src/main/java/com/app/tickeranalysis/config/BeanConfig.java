package com.app.tickeranalysis.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {
	
	@Bean
	public RestTemplate restTemplateBean(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}

}
