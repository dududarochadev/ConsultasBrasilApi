package br.dududarochadev.consultasbrasilapi.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.dududarochadev.consultasbrasilapi.dados.entidades.Marca;

public interface RepositorioDeMarca extends CrudRepository<Marca, Long> {
    @Override
    List<Marca> findAll();

    Marca findByCodigoApi(String codigoApi);
}
