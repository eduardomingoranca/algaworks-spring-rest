package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.model.input.UsuarioInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioPasswordInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioUpdateInput;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;

public interface UsuarioControllerOpenAPI {

    CollectionModel<UsuarioModel> listar();

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID do usuario invalido", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    UsuarioModel buscar(Long id);

    @ApiResponses(@ApiResponse(responseCode = "201", description = "Usuario cadastrado"))
    UsuarioModel adicionar(UsuarioInput usuarioInput);

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario atualizado"),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    UsuarioModel atualizar(Long id, UsuarioUpdateInput usuarioUpdateInput);

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario atualizado"),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    void alterarSenha(Long id, UsuarioPasswordInput senha);

}
