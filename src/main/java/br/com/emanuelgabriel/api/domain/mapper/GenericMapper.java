package br.com.emanuelgabriel.api.domain.mapper;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author emanuel.sousa
 * @since 31/08/2022
 * GenericMapper
 */

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GenericMapper implements IGenericMapper {

    private final ModelMapper modelMapper;

    @Override
    public <T> T paraObjeto(Object obj, Class<T> clazz) {
        if (Objects.isNull(obj))
            return null;
        return modelMapper.map(obj, clazz);
    }

    @Override
    public <T> T dtoParaEntidade(Object dto, Class<T> clazz) {
        if (Objects.isNull(dto))
            return null;
        return modelMapper.map(dto, clazz);
    }

    @Override
    public <T> List<T> paraLista(List<?> list, Class<T> clazz) {
        if (Objects.isNull(list) || list.isEmpty())
            return Collections.emptyList();
        return list.stream().map(obj -> paraObjeto(obj, clazz)).collect(Collectors.toList());
    }

    @Override
    public <T> Page<T> pageParaList(List<T> list, Pageable pageable) {
        if (list == null) {
            throw new IllegalArgumentException("Para criar uma Página, a lista não deve ser nula!");
        }

        int startOfPage = pageable.getPageNumber() * pageable.getPageSize();
        if (startOfPage > list.size()) {
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }

        int endOfPage = Math.min(startOfPage + pageable.getPageSize(), list.size());
        return new PageImpl<>(list.subList(startOfPage, endOfPage), pageable, list.size());

    }

    @Override
    public <T> Page<T> paraPage(Pageable pageable, Page<T> pageClass) {
        if (Objects.isNull(pageable))
            return null;
        return new PageImpl<>(pageClass.getContent(), pageable, pageClass.getTotalElements());

    }

    @Override
    public <D, T> Page<D> pageEntidadeParaPageDto(Page<T> entities, Class<D> dtoClass) {
        if (Objects.isNull(entities))
            return null;
        return entities.map(objectEntity -> modelMapper.map(objectEntity, dtoClass));
    }
}
