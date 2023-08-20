package br.dududarochadev.consultasbrasilapi.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dududarochadev.consultasbrasilapi.dados.entidades.AnoModelo;

public interface RepositorioDeAnoModelo extends JpaRepository<AnoModelo, Long> {
    @Override
    List<AnoModelo> findAll();
}
