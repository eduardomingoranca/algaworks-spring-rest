package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroRestauranteService {
    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaID = restaurante.getCozinha().getId();

        Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaID);

        restaurante.setCozinha(cozinha);

        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @Transactional
    public void ativar(Long id) {
        // nao precisa salvar no repositorio por que ao buscar um restaurante
        // a instancia do restaurante fica em um estado gerenciado pelo contexto
        // de persistencia do JPA. Qualquer alteracao sera sincronizada com o
        // banco de dados.
        Restaurante restauranteAtual = buscarOuFalhar(id);

        restauranteAtual.ativar();
    }

    @Transactional
    public void inativar(Long id) {
        Restaurante restauranteAtual = buscarOuFalhar(id);

        restauranteAtual.inativar();
    }

    @Transactional
    public Restaurante buscarOuFalhar(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

}
