package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
public class CadastroEstadoService {
    public static final String MSG_ESTADO_NAO_ENCONTRADO = "Nao existe um cadastro de estado com codigo %d";
    public static final String MSG_ESTADO_EM_USO = "Estado de codigo %d nao pode ser removido, pois esta em uso";

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    public Optional<Estado> buscar(Long id) {
        return estadoRepository.findById(id);
    }

    public void excluir(Long id) {
        try {
            estadoRepository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    format(MSG_ESTADO_NAO_ENCONTRADO, id));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    format(MSG_ESTADO_EM_USO, id));
        }
    }

    public Estado buscarOuFalhar(Long id) {
        return estadoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        format(MSG_ESTADO_NAO_ENCONTRADO, id)));
    }

}
