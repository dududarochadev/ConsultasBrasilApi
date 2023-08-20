package br.dududarochadev.consultasbrasilapi.dados.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "marca")
public class Marca {
    public Marca(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @Id
    public int id;
    public String descricao;
}
