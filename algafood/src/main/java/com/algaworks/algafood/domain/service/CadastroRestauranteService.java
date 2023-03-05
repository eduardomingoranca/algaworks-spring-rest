package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
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

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaID = restaurante.getCozinha().getId();
        Long cidadeID = restaurante.getEndereco().getCidade().getId();

        Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaID);
        Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeID);

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);

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

        ativarRestaurante(restauranteAtual);
    }

    @Transactional
    public void inativar(Long id) {
        Restaurante restauranteAtual = buscarOuFalhar(id);

        inativarRestaurante(restauranteAtual);
    }

    @Transactional
    public void fechar(Long id) {
        Restaurante restaurante = buscarOuFalhar(id);

        fecharRestaurante(restaurante);
    }

    @Transactional
    public void abrir(Long id) {
        Restaurante restaurante = buscarOuFalhar(id);

        abrirRestaurante(restaurante);
    }

    @Transactional
    public void desassociarFormaPagamento(Long codigoRestaurante, Long codigoFormaPagamento) {
        Restaurante restaurante = buscarOuFalhar(codigoRestaurante);
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(codigoFormaPagamento);

        removerFormaPagamento(formaPagamento, restaurante);
    }

    @Transactional
    public void associarFormaPagamento(Long codigoRestaurante, Long codigoFormaPagamento) {
        Restaurante restaurante = buscarOuFalhar(codigoRestaurante);
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(codigoFormaPagamento);

        adicionarFormaPagamento(formaPagamento, restaurante);
    }

    @Transactional
    public Restaurante buscarOuFalhar(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

    private void ativarRestaurante(Restaurante restauranteAtual) {
        restauranteAtual.setAtivo(true);
    }

    private void inativarRestaurante(Restaurante restauranteAtual) {
        restauranteAtual.setAtivo(false);
    }

    private void fecharRestaurante(Restaurante restaurante) {
        restaurante.setAberto(false);
    }

    private void abrirRestaurante(Restaurante restaurante) {
        restaurante.setAberto(true);
    }

    private boolean removerFormaPagamento(FormaPagamento formaPagamento, Restaurante restaurante) {
        return restaurante.getFormasPagamento().remove(formaPagamento);
    }

    private boolean adicionarFormaPagamento(FormaPagamento formaPagamento, Restaurante restaurante) {
        return restaurante.getFormasPagamento().add(formaPagamento);
    }

}
