package br.dududarochadev.consultasbrasilapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TabelaDeReferencia {
    @JsonProperty("Codigo")
    public int codigo;
    @JsonProperty("Mes")
    public String mes;
}
