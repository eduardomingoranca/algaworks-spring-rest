package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.CozinhasXMLWrapper;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
//@RequestMapping(value = "/cozinhas", produces = APPLICATION_JSON_VALUE)
@RequestMapping("/cozinhas")
public class CozinhaController {
    @Autowired
    private CozinhaRepository cozinhaRepository;

    // GET /cozinhas HTTP/1.1
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Cozinha> listar() {
        return cozinhaRepository.todas();
    }

    @GetMapping(produces = APPLICATION_XML_VALUE)
    public CozinhasXMLWrapper listarXML() {
        return new CozinhasXMLWrapper(cozinhaRepository.todas());
    }

    // cozinhas/{cozinhaId}
    @GetMapping("/{cozinhaId}")
    public Cozinha buscar(@PathVariable Long cozinhaId) {
        return cozinhaRepository.porID(cozinhaId);
    }

}
