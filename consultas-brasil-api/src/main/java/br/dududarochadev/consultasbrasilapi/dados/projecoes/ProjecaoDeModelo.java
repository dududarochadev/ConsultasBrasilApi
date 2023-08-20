package br.dududarochadev.consultasbrasilapi.dados.projecoes;

public class ProjecaoDeModelo {
    public ProjecaoDeModelo(String descricao, String marca, String dataInclusao) {
        this.descricao = descricao;
        this.marca = marca;
        this.dataInclusao = dataInclusao;
    }

    public String descricao;
    public String marca;
    public String dataInclusao;
}
