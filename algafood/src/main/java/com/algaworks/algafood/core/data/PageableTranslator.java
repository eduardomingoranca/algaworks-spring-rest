package com.algaworks.algafood.core.data;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class PageableTranslator {

    public static Pageable translate(Pageable pageable, Map<String, String> fieldsMapping) {
        // traduzindo nomeCliente para cliente.nome no momento da pesquisa
        List<Sort.Order> orders = pageable.getSort().stream()
                .filter(order -> fieldsMapping.containsKey(order.getProperty())) // se a propriedade nao existir sera ignorada
                .map(order -> new Sort.Order(order.getDirection(),
                        fieldsMapping.get(order.getProperty())))
                .collect(toList());

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
    }

}
