package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.PermissaoModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface GrupoPermissaoControllerOpenAPI {

    CollectionModel<PermissaoModel> listar(Long id);

    ResponseEntity<Void> associar(Long id, Long permissaoID);

    ResponseEntity<Void> desassociar(Long id, Long permissaoID);

}
