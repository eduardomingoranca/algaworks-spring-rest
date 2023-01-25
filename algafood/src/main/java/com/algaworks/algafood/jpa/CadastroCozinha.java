package com.algaworks.algafood.jpa;

import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class CadastroCozinha {

    @PersistenceContext
    private EntityManager manager;

    public List<Cozinha> listar() {
        // utilizando o JPQL para uma consulta da tabela cozinha no banco de dados
        return manager.createQuery("from Cozinha", Cozinha.class).getResultList();
    }

    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return manager.merge(cozinha);
    }

    public Cozinha buscar(Long id) {
        return manager.find(Cozinha.class, id);
    }

    @Transactional
    public void remover(Cozinha cozinha) {
        cozinha = buscar(cozinha.getId());
        manager.remove(cozinha);
    }

}
