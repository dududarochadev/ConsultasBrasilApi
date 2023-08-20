package br.dududarochadev.consultasbrasilapi.dados.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "anomodelo")
public class AnoModelo {
    public AnoModelo(String id, String descricao, int idModelo) {
        this.id = id;
        this.descricao = descricao;
        this.idModelo = idModelo;
    }

    @Id
    public String id;
    public String descricao;
    public int idModelo;
}
