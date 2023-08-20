package br.dududarochadev.consultasbrasilapi.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dududarochadev.consultasbrasilapi.dados.entidades.Marca;

public interface RepositorioDeMarca extends JpaRepository<Marca, Long> {
    @Override
    List<Marca> findAll();
}
