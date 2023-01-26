package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
    @Autowired
    private CadastroCidadeService cadastroCidade;

    @GetMapping
    public ResponseEntity<List<Cidade>> listar() {
        List<Cidade> cidades = cadastroCidade.listar();
        return status(OK).body(cidades);
    }

    @PostMapping
    public ResponseEntity<Object> adicionar(@RequestBody Cidade cidade) {
        try {
            cidade = cadastroCidade.salvar(cidade);
            return status(CREATED).body(cidade);

        } catch (EntidadeNaoEncontradaException e) {
            return status(BAD_REQUEST).body(e.getMessage());

        }
    }

    @PutMapping("/{cidadeID}")
    public ResponseEntity<Object> atualizar(@PathVariable("cidadeID") Long id, @RequestBody Cidade cidade) {
        try {
            Cidade cidadeAtual = cadastroCidade.porID(id);

            if (cidadeAtual == null) return status(NOT_FOUND).build();

            copyProperties(cidade, cidadeAtual, "id");
            cadastroCidade.salvar(cidadeAtual);

            return status(OK).body(cidadeAtual);
        } catch (EntidadeNaoEncontradaException e) {
            return status(BAD_REQUEST).body(e.getMessage());

        }
    }

    @DeleteMapping("/{cidadeID}")
    public ResponseEntity<Object> remover(@PathVariable("cidadeID") Long id) {
        try {
            cadastroCidade.excluir(id);
            return status(NO_CONTENT).build();

        } catch (EntidadeNaoEncontradaException e) {
            return status(NOT_FOUND).body(e.getMessage());

        } catch (EntidadeEmUsoException e) {
            return status(CONFLICT).body(e.getMessage());

        }
    }

}
