package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.model.input.UsuarioInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioPasswordInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioUpdateInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Usuarios")
public interface UsuarioControllerOpenAPI {

    @Operation(summary = "Lista os usuarios")
    CollectionModel<UsuarioModel> listar();

    @Operation(summary = "Busca um usuario por ID", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID do usuario invalido", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
    })
    UsuarioModel buscar(@Parameter(description = "ID do usuario", example = "1", required = true) Long id);

    @Operation(summary = "Cadastra um usuario", responses = {
            @ApiResponse(responseCode = "201", description = "Usuario cadastrado"),
    })
    UsuarioModel adicionar(@RequestBody(description = "Representacao de um novo usuario", required = true)
                           UsuarioInput usuarioInput);

    @Operation(summary = "Atualiza um usuario por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Usuario atualizado"),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            })
    })
    UsuarioModel atualizar(@Parameter(description = "ID do usuario", example = "1", required = true) Long id,
                           @RequestBody(description = "Representacao de um usuario com os novos dados", required = true)
                           UsuarioUpdateInput usuarioUpdateInput);

    @Operation(summary = "Atualiza a senha de um usuario", responses = {
            @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            })
    })
    ResponseEntity<Void> alterarSenha(@Parameter(description = "ID do usuario", example = "1", required = true) Long id,
                                      @RequestBody(description = "Representacao de uma nova senha", required = true)
                                      UsuarioPasswordInput senha);

}
