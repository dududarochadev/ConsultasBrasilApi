package br.dududarochadev.consultasbrasilapi.dados.entidades;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "marca")
public class Marca {
    public Marca() {
    }

    public Marca(String codigoApi, String descricao) {
        this.codigoApi = codigoApi;
        this.descricao = descricao;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String codigoApi;
    public String descricao;

    @OneToMany(mappedBy = "marca", cascade = CascadeType.ALL)
    public List<Modelo> modelos;
}
