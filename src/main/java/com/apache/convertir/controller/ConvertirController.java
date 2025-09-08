package com.apache.convertir.controller;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apache.convertir.dto.ConversionRequest;
import com.apache.convertir.dto.ConversionResponse;

@RestController
@RequestMapping("/api")
public class ConvertirController {

	@Autowired
    private ProducerTemplate producerTemplate;

    @PostMapping("/convertir")
    public ResponseEntity<ConversionResponse> convert(@RequestBody ConversionRequest request) {
        ConversionResponse response = producerTemplate.requestBody(
                "direct:convertirSync", request, ConversionResponse.class
        );
        return ResponseEntity.ok(response);
    }

}
