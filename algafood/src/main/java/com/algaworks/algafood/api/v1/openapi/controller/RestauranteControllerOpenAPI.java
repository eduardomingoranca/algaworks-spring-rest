package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
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

import java.util.List;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Restaurantes")
public interface RestauranteControllerOpenAPI {

    @Operation(parameters = {
            @Parameter(name = "projecao",
            description = "Nome da projecao",
            example = "apenas-nome",
            in = QUERY)
    })
    CollectionModel<RestauranteBasicoModel> listar();

    @Operation(hidden = true)
    CollectionModel<RestauranteApenasNomeModel> listarApenasNomes();

    @Operation(summary = "Busca um restaurante por ID", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID do restaurante invalido", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
    })
    RestauranteModel buscar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long id);

    @Operation(summary = "Cadastra um restaurante", responses = {
            @ApiResponse(responseCode = "201", description = "Restaurante cadastrado"),
    })
    RestauranteModel adicionar(@RequestBody(description = "Representação de um novo restaurante", required = true)
                               RestauranteInput restauranteInput);

    @Operation(summary = "Atualiza um restaurante por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
    })
    RestauranteModel atualizar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long id,
                               @RequestBody(description = "Representacao de um restaurante com os novos dados", required = true)
                               RestauranteInput restauranteInput);

    @Operation(summary = "Ativa um restaurante por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurante ativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema")) }),
    })
    ResponseEntity<Void> ativar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long id);

    @Operation(summary = "Desativa um restaurante por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurante inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema")) }),
    })
    ResponseEntity<Void> inativar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long id);

    @Operation(summary = "Ativa multiplos restaurantes", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso"),
    })
    ResponseEntity<Void> ativarMultiplos(@RequestBody(description = "IDs de restaurantes", required = true) List<Long> restauranteIDs);

    @Operation(summary = "Inativa multiplos restaurantes", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso"),
    })
    ResponseEntity<Void> inativarMultiplos(@RequestBody(description = "IDs de restaurantes", required = true) List<Long> restauranteIDs);

    @Operation(summary = "Fecha um restaurante por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurante fechado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema")) }),
    })
    ResponseEntity<Void> fechar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long id);

    @Operation(summary = "Abre um restaurante por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurante aberto com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema")) }),
    })
    ResponseEntity<Void> abrir(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long id);

}

