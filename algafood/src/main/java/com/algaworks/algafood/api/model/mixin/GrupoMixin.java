package com.algaworks.algafood.api.model.mixin;

import com.algaworks.algafood.domain.model.Permissao;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class GrupoMixin {

    @JsonIgnore
    private List<Permissao> permissoes = new ArrayList<>();

}
