package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroGrupoService {
    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroPermissaoService cadastroPermissao;

    @Transactional
    public List<Grupo> listar() {
        return grupoRepository.findAll();
    }

    @Transactional
    public Grupo buscarOuFalhar(Long id) {
        return grupoRepository.findById(id)
                .orElseThrow(() -> new GrupoNaoEncontradoException(id));
    }

    @Transactional
    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void associarPermissao(Long id, Long permissaoID) {
        Grupo grupo = buscarOuFalhar(id);
        Permissao permissao = cadastroPermissao.buscarOuFalhar(permissaoID);

        adicionarPermissao(grupo, permissao);
    }

    @Transactional
    public void desassociarPermissao(Long id, Long permissaoID) {
        Grupo grupo = buscarOuFalhar(id);
        Permissao permissao = cadastroPermissao.buscarOuFalhar(permissaoID);

        removerPermissao(grupo, permissao);
    }

    private void adicionarPermissao(Grupo grupo, Permissao permissao) {
        grupo.getPermissoes().add(permissao);
    }

    private void removerPermissao(Grupo grupo, Permissao permissao) {
        grupo.getPermissoes().remove(permissao);
    }

}
