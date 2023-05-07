package com.algaworks.algafood.api.v2.assembler.input.disassembler;

import com.algaworks.algafood.api.v2.model.input.CozinhaInputVersionTwo;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaInputDisassemblerVersionTwo {
    @Autowired
    private ModelMapper modelMapper;

    public Cozinha toDomainObject(CozinhaInputVersionTwo cozinhaInputVersionTwo) {
        return modelMapper.map(cozinhaInputVersionTwo, Cozinha.class);
    }

    public void copyToDomainObject(CozinhaInputVersionTwo cozinhaInputVersionTwo, Cozinha cozinha) {
        modelMapper.map(cozinhaInputVersionTwo, cozinha);
    }

}
