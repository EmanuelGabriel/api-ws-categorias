package br.com.emanuelgabriel.api.service;

import br.com.emanuelgabriel.api.domain.entity.Categoria;
import br.com.emanuelgabriel.api.domain.mapper.GenericMapper;
import br.com.emanuelgabriel.api.domain.repository.CategoriaRepository;
import br.com.emanuelgabriel.api.domain.repository.ProjecaoCategoria;
import br.com.emanuelgabriel.api.domain.repository.filter.FiltroCategoria;
import br.com.emanuelgabriel.api.domain.repository.query.CategoriaQueryRepository;
import br.com.emanuelgabriel.api.dtos.request.CategoriaParcialRequestDTO;
import br.com.emanuelgabriel.api.dtos.request.CategoriaRequestDTO;
import br.com.emanuelgabriel.api.dtos.response.CategoriaResponseDTO;
import br.com.emanuelgabriel.api.service.exceptions.CodigoCategoriaNaoEncontrado;
import br.com.emanuelgabriel.api.service.exceptions.RegraNegocioException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * @author emanuel.sousa
 */

@Service
public class CategoriaService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoriaService.class);

    private final CategoriaRepository categoriaRepository;

    private final CategoriaQueryRepository categoriaQueryRepository;

    private final GenericMapper genericMapper;

    public CategoriaService(CategoriaRepository categoriaRepository, CategoriaQueryRepository categoriaQueryRepository, GenericMapper genericMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaQueryRepository = categoriaQueryRepository;
        this.genericMapper = genericMapper;
    }

    public List<Categoria> obterCategoriasPaginadas(FiltroCategoria filtro, Pageable pageable){
        LOG.info("Buscar categorias paginadas");
        return categoriaQueryRepository.obterCategoriasPaginadaCustomizada(filtro, pageable);
    }

    public List<ProjecaoCategoria> testBuscarPorNome(String nome, String descricao) {
        return new ArrayList<>(categoriaRepository.findByNomeOrDescricao(nome, descricao, ProjecaoCategoria.class));
    }

    @Transactional
    public CategoriaResponseDTO criar(CategoriaRequestDTO requestDTO) {
        LOG.info("Criar uma nova categoria - {}", requestDTO);

        var categoriaExistente = categoriaRepository.findByNomeContainingIgnoreCase(requestDTO.getNome());

        if (categoriaExistente != null && !categoriaExistente.equals(requestDTO)) {
            throw new RegraNegocioException("Categoria de nome existente");
        }

        var categoria = genericMapper.dtoParaEntidade(requestDTO, Categoria.class);
        categoria.setAtivo(Boolean.TRUE);
        categoria.setDataCadastro(LocalDateTime.now());

        return genericMapper.paraObjeto(categoriaRepository.save(categoria), CategoriaResponseDTO.class);
    }


    public Page<CategoriaResponseDTO> buscarCategorias(Pageable pageable) {
        LOG.info("Buscar todas as categorias registradas - {}", pageable);
        var pageCategoria = categoriaRepository.findAll(pageable);
        return genericMapper.pageEntidadeParaPageDto(pageCategoria, CategoriaResponseDTO.class);
    }

    public CategoriaResponseDTO buscarCategoriaPorId(Long idCategoria) {
        LOG.info("Buscar categoria por ID: {}", idCategoria);
        var categoria = categoriaRepository.findById(idCategoria);
        if (categoria.isEmpty()) {
            throw new CodigoCategoriaNaoEncontrado("ID da categoria não encontrado");
        }

        return genericMapper.dtoParaEntidade(categoria.get(), CategoriaResponseDTO.class);
    }

    public void atualizarCategoria(Long idCategoria, CategoriaParcialRequestDTO categoriaParcialRequestDTO){
        categoriaRepository.findById(idCategoria).map(categoria -> {
            categoria.setNome(categoriaParcialRequestDTO.getNome());
            categoria.setDescricao(categoriaParcialRequestDTO.getDescricao());
            categoria.setDataAtualizacao(LocalDateTime.now());
            return genericMapper.paraObjeto(categoriaRepository.save(categoria), CategoriaResponseDTO.class);
        }).orElseThrow(() -> new CodigoCategoriaNaoEncontrado("ID da categoria não encontrado"));
    }

    public Page<CategoriaResponseDTO> buscarCategoriaPorNome(String nomeCategoria, Pageable pageable){
        LOG.info("Buscar categoria por nome: {};{}", nomeCategoria, pageable);
        var pageCategoria = categoriaRepository.findByNomeContainingIgnoreCaseOrderByNomeAsc(nomeCategoria, pageable);
        return genericMapper.pageEntidadeParaPageDto(pageCategoria, CategoriaResponseDTO.class);
    }

}
