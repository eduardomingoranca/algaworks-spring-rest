package com.algaworks.algafood.api.v1.model.mixin;

import com.algaworks.algafood.domain.model.Permissao;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public abstract class GrupoMixin {

    @JsonIgnore
    private List<Permissao> permissoes = new ArrayList<>();

}
