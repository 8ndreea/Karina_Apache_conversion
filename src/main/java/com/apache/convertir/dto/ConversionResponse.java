package com.apache.convertir.dto;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversionResponse {
	
	private BigDecimal amount;
	private String base;
	private String date;
	private Map<String, BigDecimal> rates;

	
}
