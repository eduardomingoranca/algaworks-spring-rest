package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Cidade;

import java.util.List;

public interface CidadeRepository {

    List<Cidade> todas();
    Cidade porID(Long id);
    Cidade adicionar(Cidade cidade);
    void remover(Cidade cidade);

}
