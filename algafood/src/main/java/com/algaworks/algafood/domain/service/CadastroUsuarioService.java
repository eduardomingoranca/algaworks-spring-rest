package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroUsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;


    @Transactional
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario buscarOuFalhar(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = buscarOuFalhar(usuarioId);

        if (!usuario.getSenha().equalsIgnoreCase(senhaAtual))
            throw new NegocioException("Senha atual informada nao coincide com a senha do usuario.");

        usuario.setSenha(novaSenha);
    }

}
