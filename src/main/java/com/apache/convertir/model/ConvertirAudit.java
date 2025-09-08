package com.apache.convertir.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ConvertirAudit {

	private String id;
	private String from;
	private String to;
	private BigDecimal amount;
	private LocalDateTime timestamp;
	
}
