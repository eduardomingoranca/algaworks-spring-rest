package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.model.GrupoModelAssembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/usuarios/{usuarioID}/grupos")
public class UsuarioGrupoController {
    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private GrupoModelAssembler grupoModelAssembler;

    @GetMapping
    public List<GrupoModel> listar(@PathVariable("usuarioID") Long id) {
        Usuario usuario = cadastroUsuario.buscarOuFalhar(id);

        return grupoModelAssembler.toCollectionModel(usuario.getGrupos());
    }

    @PutMapping("/{grupoID}")
    @ResponseStatus(NO_CONTENT)
    public void associar(@PathVariable("usuarioID") Long id,
                         @PathVariable Long grupoID) {
        cadastroUsuario.associarGrupo(id, grupoID);
    }

    @DeleteMapping("/{grupoID}")
    @ResponseStatus(NO_CONTENT)
    public void desassociar(@PathVariable("usuarioID") Long id,
                         @PathVariable Long grupoID) {
        cadastroUsuario.desassociarGrupo(id, grupoID);
    }

}
