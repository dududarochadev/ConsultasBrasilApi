package br.dududarochadev.consultasbrasilapi.dados.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DtoDeRespostaRequisicao<T> {
    @JsonProperty("error")
    public boolean error;
    @JsonProperty("message")
    public String message;
    @JsonProperty("response")
    public T response;
    @JsonProperty("api_limit")
    public int limiteRequisicoes;
    @JsonProperty("api_limit_for")
    public String objetoLimiteRequisicoes;
    @JsonProperty("api_limit_used")
    public int quantidadeUtilizadaRequisicoes;
}
