package com.algaworks.algafood.api.controller;

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
    @ResponseStatus(CREATED)
    public Cidade adicionar(@RequestBody Cidade cidade) {
        return cadastroCidade.salvar(cidade);
    }

    @PutMapping("/{cidadeID}")
    public Cidade atualizar(@PathVariable("cidadeID") Long id, @RequestBody Cidade cidade) {
        Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(id);
        copyProperties(cidade, cidadeAtual, "id");

        return cadastroCidade.salvar(cidadeAtual);
    }

    @DeleteMapping("/{cidadeID}")
    @ResponseStatus(NO_CONTENT)
    public void remover(@PathVariable("cidadeID") Long id) {
        cadastroCidade.excluir(id);
    }

}
