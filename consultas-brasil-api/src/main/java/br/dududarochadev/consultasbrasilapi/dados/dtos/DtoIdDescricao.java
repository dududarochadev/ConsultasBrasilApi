package br.dududarochadev.consultasbrasilapi.dados.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DtoIdDescricao {
    @JsonProperty("Value")
    public String id;
    @JsonProperty("Label")
    public String descricao;
}