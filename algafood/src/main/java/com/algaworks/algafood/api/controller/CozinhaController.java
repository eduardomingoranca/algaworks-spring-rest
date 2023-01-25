package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.CozinhasXMLWrapper;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;
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

    // ResponseEntity permite que customize a resposta HTTP
    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) {
        cozinhaRepository.porID(cozinhaId);

        HttpHeaders headers = new HttpHeaders();
        headers.add(LOCATION, "http://127.0.0.1:8080/cozinhas");

        return status(FOUND)
                .headers(headers)
                .build();
    }

}
