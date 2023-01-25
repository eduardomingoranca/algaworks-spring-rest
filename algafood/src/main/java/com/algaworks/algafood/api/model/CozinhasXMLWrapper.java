package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.model.Cozinha;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

// pode usar @JsonRootElement("cozinhas")
@JacksonXmlRootElement(localName = "cozinhas")
@Data
public class CozinhasXMLWrapper {

    // pode usar @JacksonXmlProperty(localName = "cozinha")
    @JsonProperty("cozinha")
    // desabilitando o wrapper list
    @JacksonXmlElementWrapper(useWrapping = false)
    // propriedade obrigatoria para gerar construtor
    @NonNull
    private List<Cozinha> cozinhas;

}
