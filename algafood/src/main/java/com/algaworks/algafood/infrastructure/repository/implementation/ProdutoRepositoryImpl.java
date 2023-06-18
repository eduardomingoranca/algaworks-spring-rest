package com.algaworks.algafood.infrastructure.repository.implementation;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.query.ProdutoRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public FotoProduto save(FotoProduto fotoProduto) {
        return entityManager.merge(fotoProduto);
    }

    @Transactional
    @Override
    public void delete(FotoProduto fotoProduto) {
        entityManager.remove(fotoProduto);
    }

}
