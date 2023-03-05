package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
public class CadastroUsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroGrupoService cadastroGrupo;

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
        // removendo a sincronizacao da instancia no contexto de persistencia
        usuarioRepository.detach(usuario);

        // antes de fazer uma consulta eh realizado uma atualizacao
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario))
            throw new NegocioException(format("Ja existe um usuario cadastrado com o email %s", usuario.getEmail()));

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void alterarSenha(Long id, String senhaAtual, String novaSenha) {
        Usuario usuario = buscarOuFalhar(id);

        if (!usuario.getSenha().equalsIgnoreCase(senhaAtual))
            throw new NegocioException("Senha atual informada nao coincide com a senha do usuario.");

        usuario.setSenha(novaSenha);
    }

    @Transactional
    public void associarGrupo(Long id, Long grupoID) {
        Usuario usuario = buscarOuFalhar(id);
        Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoID);

        adicionarGrupo(usuario, grupo);
    }

    @Transactional
    public void desassociarGrupo(Long grupoID, Long id) {
        Usuario usuario = buscarOuFalhar(id);
        Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoID);

        removerGrupo(usuario, grupo);
    }

    private void adicionarGrupo(Usuario usuario, Grupo grupo) {
        usuario.getGrupos().add(grupo);
    }

    private void removerGrupo(Usuario usuario, Grupo grupo) {
        usuario.getGrupos().remove(grupo);
    }

}
