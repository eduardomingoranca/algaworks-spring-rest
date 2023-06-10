package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.api.v1.model.input.CozinhaInput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@SecurityRequirement(name = "security_auth")
public interface CozinhaControllerOpenAPI {
    PagedModel<CozinhaModel> listar(Pageable pageable);

    CozinhaModel buscar(Long id);

    CozinhaModel adicionar(CozinhaInput cozinhaInput);

    CozinhaModel atualizar(Long id, CozinhaInput cozinhaInput);

    void remover(Long id);

}
