package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.CozinhasXMLWrapper;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.springframework.http.ResponseEntity.status;

@RestController
//@RequestMapping(value = "/cozinhas", produces = APPLICATION_JSON_VALUE)
@RequestMapping("/cozinhas")
public class CozinhaController {
    @Autowired
    private CozinhaRepository cozinhaRepository;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Cozinha> listar() {
        return cozinhaRepository.todas();
    }

    @ResponseStatus(OK)
    @GetMapping(produces = APPLICATION_XML_VALUE)
    public CozinhasXMLWrapper listarXML() {
        return new CozinhasXMLWrapper(cozinhaRepository.todas());
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) {
        Cozinha cozinha = cozinhaRepository.porID(cozinhaId);

        if (cozinha != null)
            return status(OK).body(cozinha);

        return status(NOT_FOUND).build();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha) {
        return cozinhaRepository.adicionar(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
        Cozinha cozinhaAtual = cozinhaRepository.porID(cozinhaId);

        if (cozinhaAtual != null) {
            cozinhaAtual.setNome(cozinha.getNome());

            cozinhaRepository.adicionar(cozinhaAtual);

            return status(OK).body(cozinhaAtual);
        }

        return status(NOT_FOUND).build();
    }

}
