package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class RestauranteModelAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public RestauranteModel toModel(Restaurante restaurante) {
        // mapeando a origem/entrada e o objeto de destino model.
        return modelMapper.map(restaurante, RestauranteModel.class);
    }

    public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
        // convertendo cada restaurante em restaurante model
//        return restaurantes.stream().map(restaurante -> toModel(restaurante)).collect(Collectors.toList());
        return restaurantes.stream().map(this::toModel).collect(toList());
    }

}
