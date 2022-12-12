package br.com.emanuelgabriel.api.domain.repository.query;

import br.com.emanuelgabriel.api.domain.entity.Categoria;
import br.com.emanuelgabriel.api.domain.repository.filter.FiltroCategoria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CategoriaQueryRepository {

    private static final Logger log = LoggerFactory.getLogger(CategoriaQueryRepository.class);
    @PersistenceContext
    private EntityManager em;

    public List<Categoria> obterCategoriasPaginadaCustomizada(FiltroCategoria filtro, Pageable pageable) {
        log.info("Buscar categorias paginadas - query customizada");

        Map<String, Object> params = new HashMap<>();
        StringBuilder query = new StringBuilder("SELECT c FROM Categoria c WHERE 1=1");

        if (filtro.getNome() != null) {
            query.append(" AND c.nome LIKE :nome");
            params.put("nome", "%" + filtro.getNome() + "%");

        }

        if (filtro.getDescricao() != null) {
            query.append(" AND c.descricao LIKE :descricao");
            params.put("descricao", "%" + filtro.getDescricao() + "%");
        }

        // order by
        query.append(" ORDER BY c.codigo");

        TypedQuery<Categoria> typedQuery = em.createQuery(query.toString(), Categoria.class);

//        if (filtro.getNome() != null) {
//            typedQuery.setParameter("nome", filtro.getNome());
//        }


        for (Map.Entry<String, Object> param : params.entrySet()) {
            typedQuery.setParameter(param.getKey(), param.getValue());
        }

        log.info("QueryString: {}", query);

        typedQuery.setMaxResults(pageable.getPageSize());
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());

        List<Categoria> categorias = typedQuery.getResultList();

        log.info("Categorias: {}", categorias);

        return categorias;
    }

}
