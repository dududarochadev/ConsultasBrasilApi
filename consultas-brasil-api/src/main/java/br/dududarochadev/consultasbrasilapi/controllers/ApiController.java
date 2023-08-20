package br.dududarochadev.consultasbrasilapi.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dududarochadev.consultasbrasilapi.services.ServicoDeApi;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private ServicoDeApi servicoDeApi;

    @GetMapping
    public void get() throws IOException, InterruptedException {
        servicoDeApi.ObterTabelaReferencia(1);
    }
}
