package com.apache.convertir.service;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apache.convertir.dto.ConversionRequest;
import com.apache.convertir.dto.ConversionResponse;

@Service
public class ConvertirService {

	@Autowired
    private ProducerTemplate producerTemplate; // Plantilla para interactuar con rutas Camel

      public ConversionResponse convertir(ConversionRequest request) {
        // Envía el request a la ruta síncrona de Camel y espera un ConversionResponse
        return producerTemplate.requestBody("direct:convertirSync", request, ConversionResponse.class);
    }

}
