package br.dududarochadev.consultasbrasilapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnoModelo {
    @JsonProperty("Value")
    public String codigo;
    @JsonProperty("Label")
    public String descricao;
}
