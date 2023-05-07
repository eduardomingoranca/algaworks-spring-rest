package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.v1.model.EnderecoModel;
import com.algaworks.algafood.api.v1.model.input.ItemPedidoInput;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    // obtendo uma instancia de model mapper
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        // customizando o mapeamento nao atribuindo o id da classe
        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        // criando um tipo de mapeamento
        TypeMap<Endereco, EnderecoModel> enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class,
                EnderecoModel.class);

        // customizando um mapeamento entre atributos origem e destino
        enderecoToEnderecoModelTypeMap.<String>addMapping(enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                (enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));

        return modelMapper;
    }

}
