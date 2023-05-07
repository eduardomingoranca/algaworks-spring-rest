package com.algaworks.algafood.api.v2.assembler.input.disassembler;

import com.algaworks.algafood.api.v2.model.input.CidadeInputVersionTwo;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeInputDisassemblerVersionTwo {
    @Autowired
    private ModelMapper modelMapper;

    public Cidade toDomainObject(CidadeInputVersionTwo cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToDomainObject(CidadeInputVersionTwo cidadeInput, Cidade cidade) {
        // Para evitar org.hibernate.HibernateException: identifier of an instance of
        // com.algaworks.algafood.domain.model.Estado was alterated from 1 to 2
        cidade.setEstado(new Estado());

        modelMapper.map(cidadeInput, cidade);
    }

}
