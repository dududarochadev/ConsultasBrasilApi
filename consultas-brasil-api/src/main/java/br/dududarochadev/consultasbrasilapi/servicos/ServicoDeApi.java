package br.dududarochadev.consultasbrasilapi.servicos;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
                Marca entidade = repositorioDeMarca.findByCodigoApi(marca.id);

                if (entidade == null) {
                    entidade = new Marca(marca.id, marca.descricao);
                }

                ObterModelos(tipoVeiculo, tabelaReferencia, entidade);

                if (Desenvolvimento) {
                    break;
                }
            }
        }
    }

    public void ObterModelos(int tipoVeiculo, String tabelaReferencia, Marca marca)
            throws IOException, InterruptedException {

        var endpoint = "/v1/vehicles/ConsultarModelos";
        var payload = "{\"codigoTabelaReferencia\": " + tabelaReferencia + ",\"codigoTipoVeiculo\": " + tipoVeiculo
                + ",\"codigoMarca\": " + marca.codigoApi
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

            var modelos = new ArrayList<Modelo>();
            for (var modelo : objetoDeResponse.response.modelos) {
                Modelo entidade = repositorioDeModelo.findByCodigoApi(modelo.id);

                if (entidade == null) {
                    entidade = new Modelo(modelo.id, modelo.descricao, new Date(), marca);
                }

                ObterAnosModelo(tipoVeiculo, tabelaReferencia, marca, entidade);

                modelos.add(entidade);
            }

            marca.modelos = modelos;
            repositorioDeMarca.save(marca);
        }
    }

    public void ObterAnosModelo(int tipoVeiculo, String tabelaReferencia, Marca marca, Modelo modelo)
            throws IOException, InterruptedException {

        var endpoint = "/v1/vehicles/ConsultarAnoModelo";
        var payload = "{\"codigoTabelaReferencia\": " + tabelaReferencia + ",\"codigoTipoVeiculo\": " + tipoVeiculo
                + ",\"codigoMarca\": " + marca.codigoApi + ",\"codigoModelo\": " + modelo.codigoApi
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
                    new TypeReference<DtoDeRespostaRequisicao<List<DtoIdDescricao>>>() {
                    });

            var anos = new ArrayList<AnoModelo>();
            for (var anoModelo : objetoDeResponse.response) {
                AnoModelo entidade = repositorioDeAnoModelo.findByCodigoApi(anoModelo.id);

                if (entidade == null) {
                    entidade = new AnoModelo(anoModelo.id, anoModelo.descricao, modelo);
                }

                anos.add(entidade);
            }

            modelo.anos = anos;
        }
    }

    private void AdicionarHeadersAutorizacao(HttpRequest.Builder request) {
        request.header("SecretKey", SECRET);
        request.header("PublicToken", PUBLIC);
        request.header("DeviceToken", DEVICE);
        request.header("Authorization", "Bearer " + TOKEN);
    }
}