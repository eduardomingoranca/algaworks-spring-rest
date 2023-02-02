package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Estado> adicionar(@RequestBody Estado estado) {
        Estado salvarEstado = cadastroEstado.salvar(estado);
        return status(CREATED).body(salvarEstado);
    }

    @PutMapping("/{estadoID}")
    public ResponseEntity<Estado> atualizar(@PathVariable("estadoID") Long id, @RequestBody Estado estado) {
        Optional<Estado> estadoAtual = cadastroEstado.buscar(id);

        if (estadoAtual.isPresent()) {
            copyProperties(estado, estadoAtual.get(), "id");
            Estado salvarEstado = cadastroEstado.salvar(estadoAtual.get());

            return status(OK).body(salvarEstado);
        }

        return status(NOT_FOUND).build();
    }

    @DeleteMapping("/{estadoID}")
    public ResponseEntity<Object> remover(@PathVariable("estadoID") Long id) {
        try {
            cadastroEstado.excluir(id);
            return status(NO_CONTENT).build();

        } catch (EntidadeNaoEncontradaException e) {
            return status(NOT_FOUND).body(e.getMessage());

        } catch (EntidadeEmUsoException e) {
            return status(CONFLICT).body(e.getMessage());

        }
    }

}
