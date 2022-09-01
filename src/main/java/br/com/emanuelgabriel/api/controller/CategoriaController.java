package br.com.emanuelgabriel.api.controller;

import br.com.emanuelgabriel.api.dtos.request.CategoriaParcialRequestDTO;
import br.com.emanuelgabriel.api.dtos.request.CategoriaRequestDTO;
import br.com.emanuelgabriel.api.dtos.response.CategoriaResponseDTO;
import br.com.emanuelgabriel.api.service.CategoriaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author emanuel.sousa
 */

@Tag(name = "Categorias", description = "Controle de gerenciamento e registro de categorias")
@RestController
@RequestMapping(value = "/v1/categorias", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoriaController {

    private static final Logger LOG = LoggerFactory.getLogger(CategoriaController.class);

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> criar(@RequestBody @Valid CategoriaRequestDTO categoriaRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.criar(categoriaRequestDTO));
    }

    @GetMapping
    public ResponseEntity<Page<CategoriaResponseDTO>> buscarCategorias(
            @PageableDefault(sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {
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

    @PutMapping(value = "{idCategoria}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> atualizarCategoriaPorId(@PathVariable Long idCategoria, @Valid @RequestBody CategoriaParcialRequestDTO categoriaParcialRequestDTO) {
        LOG.info("PUT /api/v1/categorias/{} body: {}", idCategoria, categoriaParcialRequestDTO);
        categoriaService.atualizarCategoria(idCategoria, categoriaParcialRequestDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/nome-categoria", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<CategoriaResponseDTO>> buscarCategoriaPorNome(@RequestParam(value = "nomeCategoria") String nomeCategoria,
                                                                             @PageableDefault Pageable pageable) {
        LOG.info("GET /api/v1/categorias/nome-categoria - {};{}", nomeCategoria, pageable);
        var pageCategoriaDTO = categoriaService.buscarCategoriaPorNome(nomeCategoria, pageable);
        return pageCategoriaDTO != null ? ResponseEntity.ok().body(pageCategoriaDTO) : ResponseEntity.ok().build();
    }


}
