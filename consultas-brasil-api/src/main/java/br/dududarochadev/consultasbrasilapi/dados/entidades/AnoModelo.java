package br.dududarochadev.consultasbrasilapi.dados.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "anomodelo")
public class AnoModelo {
    public AnoModelo() {
    }

    public AnoModelo(String codigoApi, String descricao, Modelo modelo) {
        this.codigoApi = codigoApi;
        this.descricao = descricao;
        this.modelo = modelo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String codigoApi;
    public String descricao;
    @ManyToOne
    @JoinColumn(name = "modelo_id")
    public Modelo modelo;
}
