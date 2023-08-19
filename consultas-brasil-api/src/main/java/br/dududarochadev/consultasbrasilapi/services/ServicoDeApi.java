package br.dududarochadev.consultasbrasilapi.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServicoDeApi {
    public String getMarcasModelos() throws IOException, InterruptedException {
        var urlBase = "https://brasilapi.com.br/api";

        var endpoint = "/fipe/tabelas/v1";

        var url = urlBase + endpoint;

        var client = HttpClient.newHttpClient();

        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());

        return response.body();
    }
}
