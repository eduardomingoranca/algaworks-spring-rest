package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroPedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    @Transactional
    public Pedido buscarOuFalhar(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
    }

}
