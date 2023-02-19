package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/estados")
public class EstadoController {
    @Autowired
    private CadastroEstadoService cadastroEstado;

    @GetMapping
    public ResponseEntity<List<Estado>> listar() {
        List<Estado> estados = cadastroEstado.listar();
        return status(OK).body(estados);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Estado adicionar(@RequestBody @Valid Estado estado) {
        return cadastroEstado.salvar(estado);
    }

    @PutMapping("/{estadoID}")
    public Estado atualizar(@PathVariable("estadoID") Long id, @RequestBody @Valid Estado estado) {
        Estado estadoAtual = cadastroEstado.buscarOuFalhar(id);
        copyProperties(estado, estadoAtual, "id");

        return cadastroEstado.salvar(estadoAtual);
    }

    @DeleteMapping("/{estadoID}")
    @ResponseStatus(NO_CONTENT)
    public void remover(@PathVariable("estadoID") Long id) {
        cadastroEstado.excluir(id);
    }

}
