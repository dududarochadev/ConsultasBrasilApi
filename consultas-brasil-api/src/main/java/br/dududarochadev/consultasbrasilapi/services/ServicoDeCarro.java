package br.dududarochadev.consultasbrasilapi.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import br.dududarochadev.consultasbrasilapi.models.Carro;
import br.dududarochadev.consultasbrasilapi.models.projecoes.ProjecaoDeCarro;
import br.dududarochadev.consultasbrasilapi.repositorios.RepositorioDeCarro;

public class ServicoDeCarro {
    @Autowired
    private RepositorioDeCarro repositorioDeCarro;

    public List<ProjecaoDeCarro> ListarModelos() {
        var carros = repositorioDeCarro.findAll();
        return Mapear(carros);
    }

    public List<ProjecaoDeCarro> ObterModeloPorAno(int ano) {
        var carros = repositorioDeCarro.findAll().stream().filter(carro -> carro.anoModelo == ano)
                .collect(Collectors.toList());
        return Mapear(carros);
    }

    public List<ProjecaoDeCarro> ObterModeloPorMarca(String marca) {
        var carros = repositorioDeCarro.findAll().stream().filter(carro -> carro.marca == marca)
                .collect(Collectors.toList());
        return Mapear(carros);
    }

    public List<ProjecaoDeCarro> ObterModeloPorData(String dataString) throws ParseException {
        var formato = new SimpleDateFormat("dd/MM/yyyy");
        var data = formato.parse(dataString);
        var carros = repositorioDeCarro.findAll().stream().filter(carro -> carro.dataInclusao == data)
                .collect(Collectors.toList());
        return Mapear(carros);
    }

    private List<ProjecaoDeCarro> Mapear(List<Carro> carros) {
        var projecaoDeCarros = new ArrayList<ProjecaoDeCarro>();
        carros.forEach(carro -> new ProjecaoDeCarro(carro.modelo, carro.marca));
        return projecaoDeCarros;
    }
}
