package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class CadastroCidadeService {
    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Cidade> listar() {
        return cidadeRepository.todas();
    }

    public Cidade salvar(Cidade cidade) {
        Long id = cidade.getEstado().getId();
        Estado estado = estadoRepository.porID(id);

        if (estado == null)
            throw new EntidadeNaoEncontradaException(
                    format("Nao existe cadastro de cidade com codigo %d", id));

        cidade.setEstado(estado);

        return cidadeRepository.adicionar(cidade);
    }

    public Cidade porID(Long id) {
        return cidadeRepository.porID(id);
    }

    public void excluir(Long id) {
        try {
            cidadeRepository.remover(id);

        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    format("Nao existe um cadastro de cidade com codigo %d", id));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    format("Cidade de codigo %d nao pode ser removida, pois esta em uso", id));
        }
    }

}
