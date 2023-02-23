package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    // obtendo uma instancia de model mapper
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        // customizando um mapeamento de tipos
        modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
                .addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);

        return modelMapper;
    }

}
