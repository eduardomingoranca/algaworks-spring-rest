package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.input.disassembler.EstadoInputAssembler;
import com.algaworks.algafood.api.assembler.model.EstadoModelAssembler;
import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.api.openapi.controller.EstadoControllerOpenAPI;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/estados", produces = APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenAPI {
    @Autowired
    private CadastroEstadoService cadastroEstado;

    @Autowired
    private EstadoModelAssembler estadoModelAssembler;

    @Autowired
    private EstadoInputAssembler estadoInputAssembler;

    @Override
    @GetMapping
    public List<EstadoModel> listar() {
        List<Estado> estados = cadastroEstado.listar();

        return estadoModelAssembler.toCollectionModel(estados);
    }

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = estadoInputAssembler.toDomainObject(estadoInput);
        Estado salvarEstado = cadastroEstado.salvar(estado);

        return estadoModelAssembler.toModel(salvarEstado);
    }

    @Override
    @PutMapping("/{estadoID}")
    public EstadoModel atualizar(@PathVariable("estadoID") Long id,
                                 @RequestBody @Valid EstadoInput estadoInput) {
        Estado estadoAtual = cadastroEstado.buscarOuFalhar(id);
        estadoInputAssembler.copyToDomainObject(estadoInput, estadoAtual);

        Estado salvarEstado = cadastroEstado.salvar(estadoAtual);

        return estadoModelAssembler.toModel(salvarEstado);
    }

}
