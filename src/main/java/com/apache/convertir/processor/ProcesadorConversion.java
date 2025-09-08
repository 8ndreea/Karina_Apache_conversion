package com.apache.convertir.processor;

import org.springframework.stereotype.Component;

import com.apache.convertir.dto.ConversionRequest;

@Component
public class ProcesadorConversion {

	public ConversionRequest validate(ConversionRequest request) {
        if (request.getAmount() == null || request.getAmount().doubleValue() <= 0) {
            throw new IllegalArgumentException("El monto ingresado debe ser mayor a 0");
        }
        if (request.getFrom() == null || request.getTo() == null) {
            throw new IllegalArgumentException("Debe especificar moneda origen y destino");
        }
        return request;
    }

}
