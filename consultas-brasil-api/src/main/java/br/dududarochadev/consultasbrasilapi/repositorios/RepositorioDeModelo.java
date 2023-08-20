package br.dududarochadev.consultasbrasilapi.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.dududarochadev.consultasbrasilapi.dados.entidades.Modelo;

public interface RepositorioDeModelo extends CrudRepository<Modelo, Long> {
    @Override
    List<Modelo> findAll();
}
