package br.com.emanuelgabriel.api.controller;

import br.com.emanuelgabriel.api.dtos.response.CategoriaResponseDTO;
import br.com.emanuelgabriel.api.service.CategoriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/categorias", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoriaController {

    private static final Logger LOG = LoggerFactory.getLogger(CategoriaController.class);

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<Page<CategoriaResponseDTO>> buscarCategorias(
            @PageableDefault(sort = "nome", direction = Sort.Direction.ASC, page = 0, size = 20) Pageable pageable) {
        LOG.info("GET /api/v1/categorias - {}", pageable);
        var pageCategorias = categoriaService.buscarCategorias(pageable);
        return pageCategorias != null ? ResponseEntity.ok().body(pageCategorias) : ResponseEntity.ok().build();
    }

    @GetMapping(value = "{idCategoria}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoriaResponseDTO> buscarCategoriaPorId(@PathVariable Long idCategoria) {
        LOG.info("GET /api/v1/categorias/{}", idCategoria);
        var categoriaResponseDTO = categoriaService.buscarCategoriaPorId(idCategoria);
        return categoriaResponseDTO != null ? ResponseEntity.ok().body(categoriaResponseDTO) : ResponseEntity.notFound().build();
    }


}
