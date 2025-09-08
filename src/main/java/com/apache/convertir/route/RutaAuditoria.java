package com.apache.convertir.route;

import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import com.apache.convertir.dto.ConversionRequest;
import com.apache.convertir.model.ConvertirAudit;

@Component
public class RutaAuditoria extends RouteBuilder {

	@Override
	public void configure() {
		from("direct:audit")
			.process(exchange -> {
		        ConversionRequest req = exchange.getIn().getBody(ConversionRequest.class);
		        ConvertirAudit audit = new ConvertirAudit();
		        audit.setId(UUID.randomUUID().toString());
		        audit.setFrom(req.getFrom());
		        audit.setTo(req.getTo());
		        audit.setAmount(req.getAmount());
		        audit.setTimestamp(LocalDateTime.now());
		        exchange.getIn().setBody(audit);
		    })
			.marshal().json(JsonLibrary.Jackson)
			.to("jms:queue:auditQueue");
	}
}
