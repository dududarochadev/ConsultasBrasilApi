package br.dududarochadev.consultasbrasilapi.servicos;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.dududarochadev.consultasbrasilapi.dados.dtos.DtoDeModelos;
import br.dududarochadev.consultasbrasilapi.dados.dtos.DtoDeRespostaRequisicao;
import br.dududarochadev.consultasbrasilapi.dados.dtos.DtoIdDescricao;
import br.dududarochadev.consultasbrasilapi.dados.entidades.AnoModelo;
import br.dududarochadev.consultasbrasilapi.dados.entidades.Marca;
import br.dududarochadev.consultasbrasilapi.dados.entidades.Modelo;
import br.dududarochadev.consultasbrasilapi.repositorios.RepositorioDeAnoModelo;
import br.dududarochadev.consultasbrasilapi.repositorios.RepositorioDeMarca;
import br.dududarochadev.consultasbrasilapi.repositorios.RepositorioDeModelo;

@Service
public class ServicoDeApi {
    @Autowired
    RepositorioDeMarca repositorioDeMarca;
    @Autowired
    RepositorioDeModelo repositorioDeModelo;
    @Autowired
    RepositorioDeAnoModelo repositorioDeAnoModelo;

    @Value("${local.desenvolvimento}")
    private boolean Desenvolvimento;
    @Value("${apibrasil.urlbase}")
    private String URL_BASE;
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
                    ObterTabelaReferencia(1);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        scheduler.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.DAYS);
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
            var objetoDeResponse = mapper.readValue(response.body().replace("Codigo", "Value").replace("Mes", "Label"),
                    new TypeReference<DtoDeRespostaRequisicao<DtoIdDescricao[]>>() {
                    });

            var tabelaAtual = objetoDeResponse.response[0];
            ObterMarcas(tipoVeiculo, tabelaAtual.id);
        }
    }

    public void ObterMarcas(int tipoVeiculo, String tabelaReferencia) throws IOException, InterruptedException {
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
                    new TypeReference<DtoDeRespostaRequisicao<DtoIdDescricao[]>>() {
                    });

            for (var marca : objetoDeResponse.response) {
                var entidade = new Marca(Integer.parseInt(marca.id), marca.descricao);
                repositorioDeMarca.save(entidade);

                ObterModelos(tipoVeiculo, tabelaReferencia, marca.id);

                if (Desenvolvimento) {
                    break;
                }
            }
        }
    }

    public void ObterModelos(int tipoVeiculo, String tabelaReferencia, String marca)
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
                    new TypeReference<DtoDeRespostaRequisicao<DtoDeModelos>>() {
                    });

            for (var modelo : objetoDeResponse.response.modelos) {
                var entidade = new Modelo(Integer.parseInt(modelo.id), modelo.descricao, Integer.parseInt(marca),
                        new Date());
                repositorioDeModelo.save(entidade);

                ObterAnosModelo(tipoVeiculo, tabelaReferencia, marca, modelo.id);
            }
        }
    }

    public void ObterAnosModelo(int tipoVeiculo, String tabelaReferencia, String marca, String modelo)
            throws IOException, InterruptedException {

        var endpoint = "/v1/vehicles/ConsultarAnoModelo";
        var payload = "{\"codigoTabelaReferencia\": " + tabelaReferencia + ",\"codigoTipoVeiculo\": " + tipoVeiculo
                + ",\"codigoMarca\": " + marca + ",\"codigoModelo\": " + modelo
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
                    new TypeReference<DtoDeRespostaRequisicao<DtoIdDescricao[]>>() {
                    });

            for (var anoModelo : objetoDeResponse.response) {
                var entidade = new AnoModelo(anoModelo.id, anoModelo.descricao,
                        Integer.parseInt(modelo));
                repositorioDeAnoModelo.save(entidade);
            }
        }
    }

    private void AdicionarHeadersAutorizacao(HttpRequest.Builder request) {
        request.header("SecretKey", SECRET);
        request.header("PublicToken", PUBLIC);
        request.header("DeviceToken", DEVICE);
        request.header("Authorization", "Bearer " + TOKEN);
    }
}