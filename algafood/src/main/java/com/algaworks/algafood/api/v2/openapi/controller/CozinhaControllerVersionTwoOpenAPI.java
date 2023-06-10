package com.algaworks.algafood.api.v2.openapi.controller;

import com.algaworks.algafood.api.v2.model.CozinhaModelVersionTwo;
import com.algaworks.algafood.api.v2.model.input.CozinhaInputVersionTwo;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface CozinhaControllerVersionTwoOpenAPI {

    PagedModel<CozinhaModelVersionTwo> listar(Pageable pageable);

    CozinhaModelVersionTwo buscar(Long id);

    CozinhaModelVersionTwo adicionar(CozinhaInputVersionTwo cozinhaInputVersionTwo);

    CozinhaModelVersionTwo atualizar(Long id, CozinhaInputVersionTwo cozinhaInputVersionTwo);

    void remover(Long id);

}
