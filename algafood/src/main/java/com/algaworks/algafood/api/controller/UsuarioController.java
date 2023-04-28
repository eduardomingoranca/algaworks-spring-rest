package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.input.disassembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.assembler.model.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.api.model.input.UsuarioPasswordInput;
import com.algaworks.algafood.api.model.input.UsuarioUpdateInput;
import com.algaworks.algafood.api.openapi.controller.UsuarioControllerOpenAPI;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/usuarios", produces = APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenAPI {
    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @Autowired
    private UsuarioInputDisassembler usuarioInputDisassembler;

    @Override
    @GetMapping
    public CollectionModel<UsuarioModel> listar() {
        List<Usuario> usuarios = cadastroUsuario.listar();

        return usuarioModelAssembler.toCollectionModel(usuarios);
    }

    @Override
    @GetMapping("/{usuarioID}")
    public UsuarioModel buscar(@PathVariable("usuarioID") Long id) {
        Usuario usuario = cadastroUsuario.buscarOuFalhar(id);

        return usuarioModelAssembler.toModel(usuario);
    }

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    public UsuarioModel adicionar(@RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
        Usuario salvarUsuario = cadastroUsuario.salvar(usuario);

        return usuarioModelAssembler.toModel(salvarUsuario);
    }

    @Override
    @PutMapping("/{usuarioID}")
    public UsuarioModel atualizar(@PathVariable("usuarioID") Long id,
                                  @RequestBody @Valid UsuarioUpdateInput usuarioUpdateInput) {
        Usuario usuario = cadastroUsuario.buscarOuFalhar(id);

        usuarioInputDisassembler.copyToDomainObject(usuarioUpdateInput, usuario);

        Usuario salvarUsuario = cadastroUsuario.salvar(usuario);

        return usuarioModelAssembler.toModel(salvarUsuario);
    }

    @Override
    @PutMapping("/{usuarioID}/senha")
    @ResponseStatus(NO_CONTENT)
    public void alterarSenha(@PathVariable("usuarioID") Long id,
                             @RequestBody @Valid UsuarioPasswordInput senha) {
        cadastroUsuario.alterarSenha(id, senha.getSenhaAtual(), senha.getNovaSenha());
    }

}
