package br.dududarochadev.consultasbrasilapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Modelo {
    @JsonProperty("Value")
    public int codigo;
    @JsonProperty("Label")
    public String descricao;
}
