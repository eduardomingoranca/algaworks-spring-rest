package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaID = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.porID(cozinhaID);

        if (cozinha == null)
            throw new EntidadeNaoEncontradaException(
                    format("Nao existe cadastro de cozinha com codigo %d", cozinhaID));

        restaurante.setCozinha(cozinha);

        return restauranteRepository.adicionar(restaurante);
    }

}
