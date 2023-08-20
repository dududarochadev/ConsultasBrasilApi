package br.dududarochadev.consultasbrasilapi.dados.entidades;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "modelo")
public class Modelo {
    public Modelo(int id, String descricao, int idMarca, Date dataInclusao) {
        this.id = id;
        this.descricao = descricao;
        this.idMarca = idMarca;
        this.dataInclusao = dataInclusao;
    }

    @Id
    public int id;
    public String descricao;
    public int idMarca;
    public Date dataInclusao;
    @OneToOne
    public Marca marca;
    @OneToMany(cascade = CascadeType.ALL)
    public List<AnoModelo> anos;
}
