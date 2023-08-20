package br.dududarochadev.consultasbrasilapi;

import org.springframework.context.annotation.Configuration;

import br.dududarochadev.consultasbrasilapi.servicos.ServicoDeApi;
import jakarta.annotation.PostConstruct;

@Configuration
public class ApplicationConfig {

    private final ServicoDeApi servicoDeApi;

    public ApplicationConfig(ServicoDeApi servicoDeApi) {
        this.servicoDeApi = servicoDeApi;
    }

    @PostConstruct
    public void iniciarServico() {
        servicoDeApi.Executar();
    }
}