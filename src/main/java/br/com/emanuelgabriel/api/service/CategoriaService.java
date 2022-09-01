package br.com.emanuelgabriel.api.service;

import br.com.emanuelgabriel.api.domain.entity.Categoria;
import br.com.emanuelgabriel.api.domain.mapper.GenericMapper;
import br.com.emanuelgabriel.api.domain.repository.CategoriaRepository;
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


/**
 * @author emanuel.sousa
 */

@Service
public class CategoriaService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoriaService.class);

    private final CategoriaRepository categoriaRepository;

    private final GenericMapper genericMapper;

    public CategoriaService(CategoriaRepository categoriaRepository, GenericMapper genericMapper) {
        this.categoriaRepository = categoriaRepository;
        this.genericMapper = genericMapper;
    }

    @Transactional
    public CategoriaResponseDTO criar(CategoriaRequestDTO requestDTO) {
        LOG.info("Criar uma nova categoria - {}", requestDTO);

        var categoriaExistente = categoriaRepository.findByNomeContainingIgnoreCase(requestDTO.getNome());

        if (categoriaExistente != null && !categoriaExistente.equals(requestDTO)) {
            throw new RegraNegocioException("Categoria de nome existente");
        }

        var categoria = genericMapper.dtoParaEntidade(requestDTO, Categoria.class);
        categoria.setAtiva(Boolean.TRUE);
        categoria.setDataCadastro(LocalDateTime.now());
        categoria.setDataAtualizacao(LocalDateTime.now());

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

}
