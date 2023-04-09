package com.algaworks.algafood.client.algafood_java_client.client.model.input;

import com.algaworks.algafood.client.algafood_java_client.client.model.input.id.CidadeIDInput;
import lombok.Data;

@Data
public class EnderecoInput {
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private CidadeIDInput cidade;

}
