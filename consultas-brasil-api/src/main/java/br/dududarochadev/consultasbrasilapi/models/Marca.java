package br.dududarochadev.consultasbrasilapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Marca {
    @JsonProperty("Value")
    public int codigo;
    @JsonProperty("Label")
    public String descricao;
}
