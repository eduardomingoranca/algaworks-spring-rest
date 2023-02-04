package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial,
                                  BigDecimal taxaFreteFinal) {
        // obtendo a instancia de criteria query construindo elementos para consulta sql
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        // criteria cria a query java de forma programatica
        // construtor de clausura
        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
        Root<Restaurante> root  = criteria.from(Restaurante.class);// from Restaurante

        var predicates = new ArrayList<>();

        if (hasText(nome)) {
        // predicate -> eh um criterio/um filtro
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%")); // like %:nome%
        }

        if (taxaFreteInicial != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"),
                    taxaFreteInicial)); // taxaFrete >= :taxaFreteInicial
        }

        if (taxaFreteFinal != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"),
                    taxaFreteFinal)); // taxaFrete <= :taxaFreteFinal
        }


        // where nome like %:nome% and taxaFrete >= :taxaFreteInicial and taxaFrete <= :taxaFreteFinal
        criteria.where(predicates.toArray(new Predicate[0]));

        // criando a query sql
        TypedQuery<Restaurante> query = manager.createQuery(criteria);
        return query.getResultList();
    }

}
