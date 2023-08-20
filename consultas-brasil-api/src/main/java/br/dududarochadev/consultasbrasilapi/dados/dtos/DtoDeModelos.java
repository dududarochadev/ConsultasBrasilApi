package br.dududarochadev.consultasbrasilapi.dados.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DtoDeModelos {
    @JsonProperty("Modelos")
    public DtoIdDescricao[] modelos;
    @JsonProperty("Anos")
    public DtoIdDescricao[] anos;
}
