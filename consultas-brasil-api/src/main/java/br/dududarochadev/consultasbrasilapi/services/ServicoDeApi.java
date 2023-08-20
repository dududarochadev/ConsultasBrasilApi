package br.dududarochadev.consultasbrasilapi.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.dududarochadev.consultasbrasilapi.models.Marca;
import br.dududarochadev.consultasbrasilapi.models.ModeloAno;
import br.dududarochadev.consultasbrasilapi.models.RespostaRequisicao;
import br.dududarochadev.consultasbrasilapi.models.TabelaDeReferencia;
import br.dududarochadev.consultasbrasilapi.models.projecoes.ProjecaoDeCarro;

@Service
public class ServicoDeApi {

    private String URL_BASE = "https://cluster-01.apigratis.com/api";
    @Value("${apibrasil.token}")
    private String TOKEN;
    @Value("${apibrasil.secret}")
    private String SECRET;
    @Value("${apibrasil.public}")
    private String PUBLIC;
    @Value("${apibrasil.device}")
    private String DEVICE;

    public void Executar() {
        var scheduler = Executors.newScheduledThreadPool(1);
        var runnable = new Runnable() {
            public void run() {
                try {
                    ObterDados();
                } catch (IOException | InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        scheduler.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.DAYS);
    }

    public void ObterDados() throws IOException, InterruptedException {
        ObterTabelaReferencia(1);
        // ObterMarcas();
        // ObterModelos();
    }

    public void ObterTabelaReferencia(int tipoVeiculo) throws IOException, InterruptedException {
        var endpoint = "/v1/vehicles/ConsultarTabelaDeReferencia";
        var payload = "{\"codigoTipoVeiculo\": " + tipoVeiculo + "}";

        var url = URL_BASE + endpoint;

        var client = HttpClient.newHttpClient();

        var requestBuilder = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .uri(URI.create(url));

        AdicionarHeadersAutorizacao(requestBuilder);

        var request = requestBuilder.build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("A requisição retornou o erro " + response.statusCode());
            return;
        }

        if (response.body() != "") {
            var mapper = new ObjectMapper();
            var objetoDeResponse = mapper.readValue(response.body(),
                    new TypeReference<RespostaRequisicao<TabelaDeReferencia[]>>() {
                    });

            var tabelaAtual = objetoDeResponse.response[0];
            System.out.println("Tabela de referencia" + tabelaAtual.codigo);

            ObterMarcas(tipoVeiculo, tabelaAtual.codigo);
        }
    }

    public void ObterMarcas(int tipoVeiculo, int tabelaReferencia) throws IOException, InterruptedException {
        var endpoint = "/v1/vehicles/ConsultarMarcas";
        var payload = "{\"codigoTabelaReferencia\": " + tabelaReferencia + ",\"codigoTipoVeiculo\": " + tipoVeiculo
                + "}";

        var url = URL_BASE + endpoint;

        var client = HttpClient.newHttpClient();

        var requestBuilder = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .uri(URI.create(url));

        AdicionarHeadersAutorizacao(requestBuilder);

        var request = requestBuilder.build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("A requisição retornou o erro " + response.statusCode());
            return;
        }

        if (response.body() != "") {
            var mapper = new ObjectMapper();
            var objetoDeResponse = mapper.readValue(response.body(),
                    new TypeReference<RespostaRequisicao<Marca[]>>() {
                    });

            var primeiraMarca = objetoDeResponse.response[0];
            System.out.println("Marca codigo " + primeiraMarca.codigo);
            System.out.println("Marca descricao " + primeiraMarca.descricao);

            ObterModelos(tipoVeiculo, tabelaReferencia, primeiraMarca.codigo);
        }
    }

    public void ObterModelos(int tipoVeiculo, int tabelaReferencia, int marca)
            throws IOException, InterruptedException {

        var endpoint = "/v1/vehicles/ConsultarModelos";
        var payload = "{\"codigoTabelaReferencia\": " + tabelaReferencia + ",\"codigoTipoVeiculo\": " + tipoVeiculo
                + ",\"codigoMarca\": " + marca
                + "}";

        var url = URL_BASE + endpoint;

        var client = HttpClient.newHttpClient();

        var requestBuilder = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .uri(URI.create(url));

        AdicionarHeadersAutorizacao(requestBuilder);

        var request = requestBuilder.build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("A requisição retornou o erro " + response.statusCode());
            return;
        }

        if (response.body() != "") {
            var mapper = new ObjectMapper();
            var objetoDeResponse = mapper.readValue(response.body(),
                    new TypeReference<RespostaRequisicao<ModeloAno>>() {
                    });

            var primeiraMarca = objetoDeResponse.response;
            System.out.println("Modelo descricao " + primeiraMarca.modelos[0].descricao);
        }
    }

    private void AdicionarHeadersAutorizacao(HttpRequest.Builder request) {
        request.header("SecretKey", SECRET);
        request.header("PublicToken", PUBLIC);
        request.header("DeviceToken", DEVICE);
        request.header("Authorization", "Bearer " + TOKEN);
    }
}