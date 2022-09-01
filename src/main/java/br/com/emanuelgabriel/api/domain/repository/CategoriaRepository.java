package br.com.emanuelgabriel.api.domain.repository;

import br.com.emanuelgabriel.api.domain.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author emanuel.sousa
 */

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Categoria findByNomeContainingIgnoreCase(String nomeCategoria);

}
