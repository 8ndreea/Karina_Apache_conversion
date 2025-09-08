package com.apache.convertir.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class RutaReporte extends RouteBuilder {

	@Override
    public void configure() {
		//transformar y mostrar la respuesta en formato Json
        from("direct:reporteJson")
            .marshal().json(JsonLibrary.Jackson);

      //transformar y mostrar la respuesta en formato Xml
      //para ello en dependencias implementamos jacksonxml
        from("direct:reporteXml")
            .marshal().jacksonXml(); 
    }
}
