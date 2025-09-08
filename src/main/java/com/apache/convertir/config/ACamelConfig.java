package com.apache.convertir.config;

import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.apache.convertir.dto.ConversionResponse;

@Configuration
public class ACamelConfig {

	@Bean
	public JacksonDataFormat jacksonDataFormat() {
		return new JacksonDataFormat(ConversionResponse.class);
	}
}
