package br.dududarochadev.consultasbrasilapi.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.dududarochadev.consultasbrasilapi.dados.entidades.AnoModelo;

public interface RepositorioDeAnoModelo extends CrudRepository<AnoModelo, Long> {
    @Override
    List<AnoModelo> findAll();

    AnoModelo findByCodigoApi(String codigoApi);
}
