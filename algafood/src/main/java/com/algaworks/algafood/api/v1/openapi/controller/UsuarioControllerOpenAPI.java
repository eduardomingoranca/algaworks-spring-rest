package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.model.input.UsuarioInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioPasswordInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioUpdateInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Usuarios")
public interface UsuarioControllerOpenAPI {

    @ApiOperation("Lista os usuarios")
    CollectionModel<UsuarioModel> listar();

    @ApiOperation("Busca um usuario por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID do usuario invalido", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    UsuarioModel buscar(@ApiParam(value = "ID de um usuario", required = true) Long id);

    @ApiOperation("Cadastra um usuario")
    @ApiResponses(@ApiResponse(responseCode = "201", description = "Usuario cadastrado"))
    UsuarioModel adicionar(@ApiParam(name = "corpo", value = "Representacao de um novo usuario", required = true)
                           UsuarioInput usuarioInput);

    @ApiOperation("Atualiza um usuario por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario atualizado"),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    UsuarioModel atualizar(@ApiParam(value = "ID de um usuario", required = true) Long id,
                           @ApiParam(name = "corpo", value = "Representacao de um usuario com os novos dados",
                                   required = true) UsuarioUpdateInput usuarioUpdateInput);

    @ApiOperation("Atualiza a senha de um usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario atualizado"),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    void alterarSenha(@ApiParam(value = "ID de um usuario", required = true) Long id,
                      @ApiParam(name = "corpo", value = "Representacao de alteracao de senha do usuario",
                              required = true) UsuarioPasswordInput senha);

}
