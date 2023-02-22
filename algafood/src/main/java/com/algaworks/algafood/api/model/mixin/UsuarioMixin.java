package com.algaworks.algafood.api.model.mixin;

import com.algaworks.algafood.domain.model.Grupo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsuarioMixin {

//    @JsonIgnore
    private OffsetDateTime dataCadastro;

    @JsonIgnore
    private List<Grupo> grupos = new ArrayList<>();

}
