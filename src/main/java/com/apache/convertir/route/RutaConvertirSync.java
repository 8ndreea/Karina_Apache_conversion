package com.apache.convertir.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import com.apache.convertir.dto.ConversionRequest;
import com.apache.convertir.dto.ConversionResponse;
import com.apache.convertir.processor.ProcesadorConversion;

@Component
public class RutaConvertirSync extends RouteBuilder {

	@Override
    public void configure() {
		
		//manejo de errores
		onException(Exception.class)
			.handled(true)
			.log("Error en ruta ${exception.stacktrace}")
			.setBody(simple("Error en procesamiento ${exception.message}"))
			.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500));
		
		//ruta sincronica para conversión de moneda
        from("direct:convertirSync")
            .routeId("conversion-sync-route")
            //convertBodyTo -> convertir los datos del DTO antes/después de consumir la API
            .convertBodyTo(ConversionRequest.class)
            .bean(ProcesadorConversion.class, "validate")
            .setHeader("from", simple("${body.from}"))
            .setHeader("to", simple("${body.to}"))
            .setHeader("amount", simple("${body.amount}"))
            //toD -> para llamar a la API
            .toD("https://api.frankfurter.dev/v1/latest?amount=${header.amount}&from=${header.from}&to=${header.to}")
            .log("Respuesta de la API ${body}")
            //unmarshal -> convertimos la respuesta de la API a un objeto Java con ayuda de JsonLibrary.Jackson
            .unmarshal().json(org.apache.camel.model.dataformat.JsonLibrary.Jackson, com.apache.convertir.dto.ConversionResponse.class)
            .log("Respuesta procesada ${body}");
    }

}
