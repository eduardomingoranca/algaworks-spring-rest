package com.algaworks.algafood.api.model.mixin;

import com.algaworks.algafood.domain.model.Grupo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsuarioMixin {

    @JsonIgnore
    private LocalDateTime dataCadastro;

    @JsonIgnore
    private List<Grupo> grupos = new ArrayList<>();

}
