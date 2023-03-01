package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.repository.CustomJPARepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class CustomJPARepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID>
        implements CustomJPARepository<T, ID> {
    private final EntityManager manager;

    public CustomJPARepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
                                   EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.manager = entityManager;
    }

    @Override
    public Optional<T> buscarPrimeiro() {
//         getDomainClass -> retorna a classe da entidade
//         from NomeDaClasse
        var jpql = "from " + getDomainClass().getName();

//         retona um resultado limitando o resultado em apenas um registro
        T entity = manager.createQuery(jpql, getDomainClass())
                .setMaxResults(1)
                .getSingleResult();

//         pode ser um valor nulo.
        return ofNullable(entity);
    }

    @Override
    public void detach(T entity) {
        manager.detach(entity);
    }

}
