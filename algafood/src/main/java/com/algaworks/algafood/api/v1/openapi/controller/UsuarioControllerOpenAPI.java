package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.model.input.UsuarioInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioPasswordInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioUpdateInput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
public interface UsuarioControllerOpenAPI {

    CollectionModel<UsuarioModel> listar();

    UsuarioModel buscar(Long id);

    UsuarioModel adicionar(UsuarioInput usuarioInput);

    UsuarioModel atualizar(Long id, UsuarioUpdateInput usuarioUpdateInput);

    void alterarSenha(Long id, UsuarioPasswordInput senha);

}
