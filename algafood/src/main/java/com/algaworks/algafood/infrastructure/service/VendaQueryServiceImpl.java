package com.algaworks.algafood.infrastructure.service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.query.VendaQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

// anotato como repositorio porque o spring traduz algumas exceptions para exceptions de persistencia
// do spring
@Repository
public class VendaQueryServiceImpl implements VendaQueryService {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {
        // implementando a consulta
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        // especificando o tipo da consulta/tipo do retorno
        CriteriaQuery<VendaDiaria> query = builder.createQuery(VendaDiaria.class);
        // especificando a entidade que sera consultada
        Root<Pedido> root = query.from(Pedido.class);

        // criando uma expressao para uma funcao no banco de dados
        Expression<Date> functionDateDataCriacao = builder.function("date",
                Date.class, root.get("dataCriacao"));

        // realizando a pesquisa/busca
        CompoundSelection<VendaDiaria> selection = builder.construct(VendaDiaria.class,
                functionDateDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));

        query.select(selection);
        query.groupBy(functionDateDataCriacao);

        // retorna uma lista de vendas diarias
        return manager.createQuery(query).getResultList();
    }

}
