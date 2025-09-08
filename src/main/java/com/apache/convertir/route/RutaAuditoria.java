package com.apache.convertir.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class RutaAuditoria extends RouteBuilder {

	@Override
	public void configure() {
		from("direct:audit")
			.marshal().json(JsonLibrary.Jackson)
			.to("jms:queue:auditQueue");
	}
}
