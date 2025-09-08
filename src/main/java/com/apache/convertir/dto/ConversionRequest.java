package com.apache.convertir.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ConversionRequest {

	private BigDecimal amount;
	private String from;
	private String to;
}
