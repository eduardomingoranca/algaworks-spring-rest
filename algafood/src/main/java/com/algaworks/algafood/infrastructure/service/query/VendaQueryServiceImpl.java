package com.algaworks.algafood.infrastructure.service.query;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.query.VendaQueryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.algaworks.algafood.domain.enumeration.StatusPedido.CONFIRMADO;
import static com.algaworks.algafood.domain.enumeration.StatusPedido.ENTREGUE;

// anotato como repositorio porque o spring traduz algumas exceptions para exceptions de persistencia
// do spring
@Repository
public class VendaQueryServiceImpl implements VendaQueryService {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
        // implementando a consulta
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        // especificando o tipo da consulta/tipo do retorno
        CriteriaQuery<VendaDiaria> query = builder.createQuery(VendaDiaria.class);
        // especificando a entidade que sera consultada
        Root<Pedido> root = query.from(Pedido.class);

        // criando uma expressao para uma funcao no banco de dados
        Expression<Date> functionConvertTzDataCriacao = builder.function("convert_tz",
                Date.class, root.get("dataCriacao"), builder.literal("+00:00"), builder.literal(timeOffset));

        Expression<Date> functionDateDataCriacao = builder.function("date",
                Date.class, functionConvertTzDataCriacao);

        // realizando a pesquisa/busca
        CompoundSelection<VendaDiaria> selection = builder.construct(VendaDiaria.class,
                functionDateDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));

        Predicate predicate = usandoFiltroVendas(filtro, builder, root);

        query.select(selection);
        query.where(predicate);
        query.groupBy(functionDateDataCriacao);

        // retorna uma lista de vendas diarias
        return manager.createQuery(query).getResultList();
    }

    private Predicate usandoFiltroVendas(VendaDiariaFilter filtro,
                                         CriteriaBuilder builder,
                                         Root<Pedido> root) {
        // criando uma lista de filtros
        List<Predicate> predicates = new ArrayList<>();

        if (filtro.getRestauranteId() != null)
            predicates.add(builder.equal(root.get("restaurante").get("id"), filtro.getRestauranteId()));

        // data de criacao do pedido deve ser maior que a data de criacao de inicio
        if (filtro.getDataCriacaoInicio() != null)
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"),
                    filtro.getDataCriacaoInicio()));

        // data de criacao do pedido deve ser menor que a data de criacao final
        if (filtro.getDataCriacaoFim() != null)
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"),
                    filtro.getDataCriacaoFim()));

        predicates.add(root.get("status").in(CONFIRMADO, ENTREGUE));

        return builder.and(predicates.toArray(new Predicate[0]));
    }

}
