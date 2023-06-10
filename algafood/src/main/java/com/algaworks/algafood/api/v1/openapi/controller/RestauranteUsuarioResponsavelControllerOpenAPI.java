package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.UsuarioModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface RestauranteUsuarioResponsavelControllerOpenAPI {

    CollectionModel<UsuarioModel> listar(Long id);

    ResponseEntity<Void> associar(Long id, Long usuarioID);

    ResponseEntity<Void> desassociar(Long id, Long usuarioID);

}
