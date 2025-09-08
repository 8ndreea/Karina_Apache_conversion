package com.apache.convertir.service;

import java.util.List;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apache.convertir.dto.ConversionResponse;

@Service
public class ReporteService {
    @Autowired
    private ProducerTemplate producerTemplate;
    //Genera un reporte en formato JSON a partir de una lista de conversiones.

    public String generarReporteJson(List<ConversionResponse> responses) {
        return producerTemplate.requestBody("direct:reporteJson", responses, String.class);
    }

//Genera un reporte en formato XML a partir de una lista de conversiones.
    public String generarReporteXml(List<ConversionResponse> responses) {
        return producerTemplate.requestBody("direct:reporteXml", responses, String.class);
    }

}
