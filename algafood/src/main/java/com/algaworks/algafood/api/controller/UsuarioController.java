package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.input.disassembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.assembler.model.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.api.model.input.UsuarioPasswordInput;
import com.algaworks.algafood.api.model.input.UsuarioUpdateInput;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @Autowired
    private UsuarioInputDisassembler usuarioInputDisassembler;

    @GetMapping
    public List<UsuarioModel> listar() {
        List<Usuario> usuarios = cadastroUsuario.listar();

        return usuarioModelAssembler.toCollectionModel(usuarios);
    }

    @GetMapping("/{usuarioID}")
    public UsuarioModel buscar(@PathVariable("usuarioID") Long id) {
        Usuario usuario = cadastroUsuario.buscarOuFalhar(id);

        return usuarioModelAssembler.toModel(usuario);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public UsuarioModel adicionar(@RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
        Usuario salvarUsuario = cadastroUsuario.salvar(usuario);

        return usuarioModelAssembler.toModel(salvarUsuario);
    }

    @PutMapping("/{usuarioID}")
    public UsuarioModel atualizar(@PathVariable("usuarioID") Long id,
                                  @RequestBody @Valid UsuarioUpdateInput usuarioUpdateInput) {
        Usuario usuario = cadastroUsuario.buscarOuFalhar(id);

        usuarioInputDisassembler.copyToDomainObject(usuarioUpdateInput, usuario);

        Usuario salvarUsuario = cadastroUsuario.salvar(usuario);

        return usuarioModelAssembler.toModel(salvarUsuario);
    }

    @PutMapping("/{usuarioID}/senha")
    @ResponseStatus(NO_CONTENT)
    public void alterarSenha(@PathVariable("usuarioID") Long id,
                             @RequestBody @Valid UsuarioPasswordInput senha) {
        cadastroUsuario.alterarSenha(id, senha.getSenhaAtual(), senha.getNovaSenha());
    }

}
