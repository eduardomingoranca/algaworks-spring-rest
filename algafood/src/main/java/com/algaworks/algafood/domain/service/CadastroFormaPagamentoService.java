package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroFormaPagamentoService {
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;


    @Transactional
    public List<FormaPagamento> listar() {
        return formaPagamentoRepository.findAll();
    }

    @Transactional
    public FormaPagamento buscarOuFalhar(Long id) {
        return formaPagamentoRepository.findById(id)
                .orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
    }

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

}
