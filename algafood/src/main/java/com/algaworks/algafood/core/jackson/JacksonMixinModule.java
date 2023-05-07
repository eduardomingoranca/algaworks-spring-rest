package com.algaworks.algafood.core.jackson;

import com.algaworks.algafood.api.v1.model.mixin.*;
import com.algaworks.algafood.domain.model.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;


@Component
public class JacksonMixinModule extends SimpleModule {

    // registrando o mixin
    public JacksonMixinModule() {
        // unindo as classes original e mixin
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
        setMixInAnnotation(Usuario.class, UsuarioMixin.class);
        setMixInAnnotation(Grupo.class, GrupoMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
    }

}
