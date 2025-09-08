package com.apache.convertir.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import com.apache.convertir.dto.ConversionRequest;
import com.apache.convertir.dto.ConversionResponse;
import com.apache.convertir.processor.ProcesadorConversion;

@Component
public class RutaConvertirAsync extends RouteBuilder {

	@Override
    public void configure() {
        from("jms:queue:conversionQueue")
            .routeId("conversion-async-route")
            .wireTap("direct:audit")
            .convertBodyTo(ConversionRequest.class)
            .bean(ProcesadorConversion.class, "validate")
            .setHeader("from", simple("${body.from}"))
            .setHeader("to", simple("${body.to}"))
            .setHeader("amount", simple("${body.amount}"))
            .toD("https://api.frankfurter.dev/v1/latest?amount=${header.amount}&from=${header.from}&to=${header.to}")
            .unmarshal().json(JsonLibrary.Jackson, ConversionResponse.class)
            .aggregate(constant(true), new org.apache.camel.processor.aggregate.GroupedBodyAggregationStrategy())
                .completionSize(5)
            .marshal().json(JsonLibrary.Jackson)
            .log("mensaje recibido: ${body}")
            .to("log:conversionResult");
    }

}
