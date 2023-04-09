package com.algaworks.algafood.client.algafood_java_client.client.model.response;

import com.algaworks.algafood.client.algafood_java_client.client.model.response.resumo.CidadeResumoModel;
import lombok.Data;

@Data
public class EnderecoModel {
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private CidadeResumoModel cidade;

}
