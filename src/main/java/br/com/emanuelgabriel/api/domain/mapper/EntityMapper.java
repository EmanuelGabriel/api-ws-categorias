package br.com.emanuelgabriel.api.domain.mapper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 
 * @author emanuel.sousa
 * @since 28/10/2021
 * @version 1.0
 * @param <DTO>    - DTO tipo de parâmetro.
 * @param <ENTITY> - ENTITY tipo de parâmetro.
 *                 {@link emanuel.gabriel.sousa@hotmail.com.br}
 */

public interface EntityMapper<DTO, ENTITY> {

	/**
	 * converter um DTO para uma ENTITY
	 * 
	 * @author emanuel.sousa
	 * @param dto
	 * @return <ENTITY> entity
	 */
	public ENTITY toEntity(DTO dto);

	/**
	 * converter uma ENTITY para um DTO
	 * 
	 * @author emanuel.sousa
	 * @param entity
	 * @return <DTO> dto
	 */
	public DTO toDto(ENTITY entity);

	/**
	 * converter uma List DTO para uma list ENTITY
	 * 
	 * @author emanuel.sousa
	 * @param dtoList
	 * @return List<ENTITY> list entity
	 */
	public List<ENTITY> toEntity(List<DTO> dtoList);

	/**
	 * converter uma list ENTITY para uma lista DTO
	 * 
	 * @author emanuel.sousa
	 * @param entityList
	 * @return List<DTO>
	 */
	public List<DTO> toDto(List<ENTITY> entityList);

	/**
	 * 
	 * @param pageable
	 * @param pageEntity
	 * @return page DTO
	 */
	public Page<DTO> mapEntityPageToDTO(Pageable pageable, Page<ENTITY> pageEntity);

}
