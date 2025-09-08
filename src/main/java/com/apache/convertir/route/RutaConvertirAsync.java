package com.apache.convertir.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.GroupedBodyAggregationStrategy;
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
            //wireTap -> enviamos una copia de cada solicitud/respuesta a la cola de auditoría
            .wireTap("direct:audit")
            .convertBodyTo(ConversionRequest.class)
            //bean -> para aplicar lógica de negocio encapsulada en un método,
            //en este caso validar que el input se válido y completo.
            .bean(ProcesadorConversion.class, "validate")
            .setHeader("from", simple("${body.from}"))
            .setHeader("to", simple("${body.to}"))
            .setHeader("amount", simple("${body.amount}"))
            .toD("https://api.frankfurter.dev/v1/latest?amount=${header.amount}&from=${header.from}&to=${header.to}")
            .unmarshal().json(JsonLibrary.Jackson, ConversionResponse.class)
            //GroupedBodyAggregationStrategy -> estrategia de agregación, para "combinar" cuerpos de varios mensajes entrantes en uno solo cohesivo 'cola / pila'.
            //En este programa se generan agrupaciones de 5 conversiones.
            //GroupedBodyAggregationStrategy es usado por una subclase y se llama para recuperar una instancia del valor que se agregará y reenviará al punto final receptor.
            .aggregate(constant(true), new GroupedBodyAggregationStrategy())
                .completionSize(5)
            .marshal().json(JsonLibrary.Jackson)
            .log("mensaje recibido: ${body}")
            .to("log:conversionResult");
    }

}
