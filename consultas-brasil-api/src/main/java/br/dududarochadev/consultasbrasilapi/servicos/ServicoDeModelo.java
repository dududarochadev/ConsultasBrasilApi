package br.dududarochadev.consultasbrasilapi.servicos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        var modelos = repositorioDeModelo.findAll();
        return Mapear(modelos);
    }

    public List<ProjecaoDeModelo> ObterModeloPorAno(String idAno) {
        var modelos = repositorioDeModelo.findAll().stream()
                .filter(modelo -> modelo.anos.stream().anyMatch(ano -> ano.id == idAno))
                .collect(Collectors.toList());
        return Mapear(modelos);
    }

    public List<ProjecaoDeModelo> ObterModeloPorMarca(String idMarca) {
        var modelos = repositorioDeModelo.findAll().stream()
                .filter(modelo -> modelo.idMarca == Integer.parseInt(idMarca))
                .collect(Collectors.toList());
        return Mapear(modelos);
    }

    public List<ProjecaoDeModelo> ObterModeloPorData(String dataString) throws ParseException {
        var formato = new SimpleDateFormat("dd/MM/yyyy");
        var data = formato.parse(dataString);
        var modelos = repositorioDeModelo.findAll().stream().filter(modelo -> modelo.dataInclusao == data)
                .collect(Collectors.toList());
        return Mapear(modelos);
    }

    private List<ProjecaoDeModelo> Mapear(List<Modelo> modelos) {
        var projecaoDeModelos = new ArrayList<ProjecaoDeModelo>();
        modelos.forEach(modelo -> new ProjecaoDeModelo(modelo.descricao, modelo.marca.descricao,
                modelo.dataInclusao.toString()));
        return projecaoDeModelos;
    }
}
