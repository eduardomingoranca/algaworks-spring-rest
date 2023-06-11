package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.api.v1.model.input.CozinhaInput;
import com.algaworks.algafood.core.springdoc.parameter.PageableParameter;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@SecurityRequirement(name = "security_auth")
public interface CozinhaControllerOpenAPI {

    @PageableParameter
    PagedModel<CozinhaModel> listar(@Parameter(hidden = true) Pageable pageable);

    CozinhaModel buscar(Long id);

    CozinhaModel adicionar(CozinhaInput cozinhaInput);

    CozinhaModel atualizar(Long id, CozinhaInput cozinhaInput);

    void remover(Long id);

}
