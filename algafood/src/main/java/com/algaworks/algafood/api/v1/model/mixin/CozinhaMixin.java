package com.algaworks.algafood.api.v1.model.mixin;

import com.algaworks.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public abstract class CozinhaMixin {

    // nao serializa a entidade restaurantes para json
    @JsonIgnore
    private List<Restaurante> restaurantes = new ArrayList<>();

}
