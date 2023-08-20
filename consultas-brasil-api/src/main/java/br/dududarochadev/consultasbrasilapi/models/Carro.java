package br.dududarochadev.consultasbrasilapi.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "carro")
public class Carro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String valor;
    public String marca;
    public String modelo;
    public int anoModelo;
    public String combustivel;
    public String codigoFipe;
    public String mesReferencia;
    public int tipoVeiculo;
    public String siglaCombustivel;
    public String dataConsulta;
    public Date dataInclusao;
}
