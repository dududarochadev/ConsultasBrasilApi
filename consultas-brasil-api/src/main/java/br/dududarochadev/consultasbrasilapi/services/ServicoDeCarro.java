package br.dududarochadev.consultasbrasilapi.services;

import java.util.ArrayList;
import java.util.List;

import br.dududarochadev.consultasbrasilapi.models.projecoes.ProjecaoDeCarro;

public class ServicoDeCarro {
    public List<ProjecaoDeCarro> ListarModelos() {
        return new ArrayList<ProjecaoDeCarro>();
    }

    public List<ProjecaoDeCarro> ObterModeloPorAno(int ano) {
        return new ArrayList<ProjecaoDeCarro>();
    }

    public List<ProjecaoDeCarro> ObterModeloPorMarca(String marca) {
        return new ArrayList<ProjecaoDeCarro>();
    }

    public List<ProjecaoDeCarro> ObterModeloPorDia(int dia, int mes, int ano) {
        return new ArrayList<ProjecaoDeCarro>();
    }
}
