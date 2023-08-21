package br.dududarochadev.consultasbrasilapi.dados.entidades;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "modelo")
public class Modelo {
    public Modelo() {
    }

    public Modelo(String codigoApi, String descricao, Date dataInclusao, Marca marca) {
        this.codigoApi = codigoApi;
        this.descricao = descricao;
        this.dataInclusao = dataInclusao;
        this.marca = marca;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String codigoApi;
    public String descricao;
    public Date dataInclusao;
    @ManyToOne
    @JoinColumn(name = "marca_id")
    public Marca marca;
    @OneToMany(mappedBy = "modelo", cascade = CascadeType.ALL)
    public List<AnoModelo> anos;
}
