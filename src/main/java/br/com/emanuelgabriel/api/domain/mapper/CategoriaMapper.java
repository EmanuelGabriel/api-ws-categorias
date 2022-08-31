package br.com.emanuelgabriel.api.domain.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.com.emanuelgabriel.api.domain.entity.Categoria;
import br.com.emanuelgabriel.api.dtos.response.CategoriaResponseDTO;

/**
 * 
 * @author emanuel.sousa
 *
 */

@Component
public class CategoriaMapper implements EntityMapper<CategoriaResponseDTO, Categoria> {

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Categoria toEntity(CategoriaResponseDTO dto) {
		return modelMapper.map(dto, Categoria.class);
	}

	@Override
	public CategoriaResponseDTO toDto(Categoria entity) {
		return modelMapper.map(entity, CategoriaResponseDTO.class);
	}

	@Override
	public List<Categoria> toEntity(List<CategoriaResponseDTO> dtoList) {
		return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
	}

	@Override
	public List<CategoriaResponseDTO> toDto(List<Categoria> entityList) {
		return entityList.stream().map(this::toDto).collect(Collectors.toList());
	}

	@Override
	public Page<CategoriaResponseDTO> mapEntityPageToDTO(Pageable pageable, Page<Categoria> pageEntity) {
		var categoriaDto = toDto(pageEntity.getContent());
		return new PageImpl<>(categoriaDto, pageable, pageEntity.getTotalElements());
	}

}
