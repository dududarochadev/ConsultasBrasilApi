package br.dududarochadev.consultasbrasilapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModeloAno {
    @JsonProperty("Modelos")
    public Modelo[] modelos;
    @JsonProperty("Anos")
    public AnoModelo[] anos;
}
