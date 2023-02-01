package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Cozinha;

import java.util.List;

public interface CozinhaRepository {

    List<Cozinha> todas();
    List<Cozinha> consultarPorNome(String nome);
    Cozinha porID(Long id);
    Cozinha adicionar(Cozinha cozinha);
    void remover(Long id);

}
