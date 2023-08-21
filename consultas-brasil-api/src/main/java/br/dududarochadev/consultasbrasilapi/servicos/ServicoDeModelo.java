package br.dududarochadev.consultasbrasilapi.servicos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dududarochadev.consultasbrasilapi.dados.entidades.Modelo;
import br.dududarochadev.consultasbrasilapi.dados.projecoes.ProjecaoDeModelo;
import br.dududarochadev.consultasbrasilapi.repositorios.RepositorioDeModelo;

@Service
public class ServicoDeModelo {
    @Autowired
    private RepositorioDeModelo repositorioDeModelo;

    public List<ProjecaoDeModelo> ListarModelos() {
        var modelos = repositorioDeModelo.findAllWithMarca();
        return Mapear(modelos);
    }

    public List<ProjecaoDeModelo> ObterModeloPorAno(String idAno) {
        var modelos = repositorioDeModelo.findAll().stream()
                .filter(modelo -> modelo.anos.stream().anyMatch(ano -> ano.codigoApi.equals(idAno)))
                .collect(Collectors.toList());
        return Mapear(modelos);
    }

    public List<ProjecaoDeModelo> ObterModeloPorMarca(String idMarca) {
        var modelos = repositorioDeModelo.findAllWithMarca().stream()
                .filter(modelo -> modelo.marca.codigoApi.equals(idMarca))
                .collect(Collectors.toList());
        return Mapear(modelos);
    }

    public List<ProjecaoDeModelo> ObterModeloPorData(String dataString) throws ParseException {
        var formato = new SimpleDateFormat("dd-MM-yyyy");
        var data = formato.parse(dataString).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        var modelos = repositorioDeModelo.findAll().stream()
                .filter(modelo -> modelo.dataInclusao.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                        .equals(data))
                .collect(Collectors.toList());

        return Mapear(modelos);
    }

    private List<ProjecaoDeModelo> Mapear(List<Modelo> modelos) {
        var projecoesDeModelo = new ArrayList<ProjecaoDeModelo>();
        modelos.forEach(modelo -> projecoesDeModelo.add(new ProjecaoDeModelo(modelo.descricao, modelo.marca.descricao,
                modelo.dataInclusao.toString())));
        return projecoesDeModelo;
    }
}
