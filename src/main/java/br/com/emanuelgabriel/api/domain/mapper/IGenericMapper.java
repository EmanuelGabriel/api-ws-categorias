package br.com.emanuelgabriel.api.domain.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author emanuel.sousa
 * @since 31/08/2022
 * {@summary interface genérica do mapper}
 */
public interface IGenericMapper {

    /**
     * Responsável em converter um objeto para outro tipo
     *
     * @param <T>
     * @param obj
     * @param clazz
     * @return <T>
     * @author emanuel.sousa
     */
    <T> T paraObjeto(Object obj, Class<T> clazz);

    /**
     * @param <T>
     * @param dto
     * @param clazz
     * @return <T>
     * @author emanuel.sousa
     */
    <T> T dtoParaEntidade(Object dto, Class<T> clazz);

    /**
     * Responsável em converter uma lista de objeto
     *
     * @param <T>
     * @param list
     * @param clazz
     * @return <T>
     * @author emanuel.sousa
     */
    <T> List<T> paraLista(List<?> list, Class<T> clazz);

    /**
     * @param <T>
     * @param list
     * @param pageable
     * @return
     */
    <T> Page<T> pageParaList(List<T> list, Pageable pageable);

    /**
     * @param <T>
     * @param pageable
     * @param pageClass
     * @return
     */
    <T> Page<T> paraPage(Pageable pageable, Page<T> pageClass);

    /**
     * Mapeia a página {@code entity} do tipo <code>T</code> que deve ser mapeada como entrada para a página {@code dtoClass}
     * de objeto mapeado com tipo <code>D</code>.
     *
     * @param <D>      - tipo de objetos na página de resultados
     * @param <T>      - tipo de entidade em <code>entityPage</code>
     * @param entities - página de entidades que precisam ser mapeadas
     * @param dtoClass - classe do elemento da página de resultados
     * @return page - página mapeada com objetos do tipo <code>D</code>..
     * @NB <code>dtoClass</code> deve ter NoArgsConstructor!
     */
    <D, T> Page<D> pageEntidadeParaPageDto(Page<T> entities, Class<D> dtoClass);

}
