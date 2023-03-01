package com.algaworks.algafood.api.assembler.input.disassembler;

import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.api.model.input.UsuarioUpdateInput;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioInputDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public Usuario toDomainObject(UsuarioInput usuarioInput) {
        return modelMapper.map(usuarioInput, Usuario.class);
    }

    public void copyToDomainObject(UsuarioUpdateInput usuarioUpdateInput, Usuario usuario) {
        modelMapper.map(usuarioUpdateInput, usuario);
    }

}
