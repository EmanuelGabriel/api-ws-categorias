package br.com.emanuelgabriel.api.controller;

import br.com.emanuelgabriel.api.domain.entity.Categoria;
import br.com.emanuelgabriel.api.domain.repository.ProjecaoCategoria;
import br.com.emanuelgabriel.api.domain.repository.filter.FiltroCategoria;
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
import java.util.List;

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

    @GetMapping(value = "/nome", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProjecaoCategoria>> testeBuscarCategoriaPorNome(@RequestParam(value = "nomeCategoria", required = false) String nomeCategoria,
                                                                               @RequestParam(value = "descricao", required = false) String descricao) {
        LOG.info("GET /api/v1/categorias/nome - {};{}", nomeCategoria, descricao);
        var categoriaResponse = categoriaService.testBuscarPorNome(nomeCategoria, descricao);
        return categoriaResponse != null ? ResponseEntity.ok().body(categoriaResponse) : ResponseEntity.ok().build();
    }

    @GetMapping(value = "paginada-por-nome")
    public ResponseEntity<List<Categoria>> obterCategoriasPaginadas(
            FiltroCategoria filtro,
            @PageableDefault(page = 0, size = 5) Pageable pageable){
        LOG.info("GET /api/v1/categorias/paginada-por-nome/{}", filtro);
        var categorias = categoriaService.obterCategoriasPaginadas(filtro, pageable);
        return categorias != null ? ResponseEntity.ok().body(categorias) : ResponseEntity.ok().build();
    }

    @GetMapping(value = "buscar-por-nome")
    public ResponseEntity<List<Categoria>> listarCategoriasPaginadasPor(
            @RequestParam(value = "nomeCategoria", required = false) String nomeCategoria,
            @PageableDefault(page = 0, size = 10) Pageable pageable){
        LOG.info("GET /api/v1/categorias/buscar-por-nome/{} - page: {}; size: {}", nomeCategoria, pageable.getPageNumber(), pageable.getPageSize());
        var categorias = categoriaService.listarCategoriasPaginadasPor(nomeCategoria, pageable);
        return categorias != null ? ResponseEntity.ok().body(categorias) : ResponseEntity.ok().build();
    }

}
