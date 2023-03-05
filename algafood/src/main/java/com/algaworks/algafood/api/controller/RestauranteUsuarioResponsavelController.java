package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.model.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/restaurantes/{restauranteID}/responsaveis")
public class RestauranteUsuarioResponsavelController {
    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @GetMapping
    public List<UsuarioModel> listar(@PathVariable("restauranteID") Long id) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(id);

        return usuarioModelAssembler.toCollectionModel(restaurante.getUsuarios());
    }

    @PutMapping("/{usuarioID}")
    @ResponseStatus(NO_CONTENT)
    public void associar(@PathVariable("restauranteID") Long id,
                         @PathVariable Long usuarioID) {
        cadastroRestaurante.associarUsuarioResponsavel(id, usuarioID);
    }

    @DeleteMapping("/{usuarioID}")
    @ResponseStatus(NO_CONTENT)
    public void desassociar(@PathVariable("restauranteID") Long id,
                            @PathVariable Long usuarioID) {
        cadastroRestaurante.desassociarUsuarioResponsavel(id, usuarioID);
    }

}
