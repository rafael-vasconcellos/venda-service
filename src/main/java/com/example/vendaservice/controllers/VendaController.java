package com.example.vendaservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vendaservice.venda.VendaDTO;


@RestController
@RequestMapping("/api")
public class VendaController {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/venda")
    public ResponseEntity<?> register(@RequestBody VendaDTO venda) { 
        if (venda.id() != null) { 
            kafkaTemplate.send("estoque-topico", venda.id());
            return ResponseEntity.ok("Venda registrada com sucesso!");
        }

        return ResponseEntity.badRequest().build();
    }
}
