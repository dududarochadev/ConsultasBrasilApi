package br.dududarochadev.consultasbrasilapi.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import br.dududarochadev.consultasbrasilapi.models.Carro;

public interface RepositorioDeCarro extends JpaRepository<Carro, Long> {

}
