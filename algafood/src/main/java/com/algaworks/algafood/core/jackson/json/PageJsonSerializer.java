package com.algaworks.algafood.core.jackson.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;

@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>> {

    @Override
    public void serialize(Page<?> page, JsonGenerator gen,
                          SerializerProvider serializers) throws IOException {
        // iniciando um objeto no json
        gen.writeStartObject();

        // escrevendo uma propriedade de objeto
        gen.writeObjectField("content", page.getContent());

        // adicionando propriedades de paginacao
        gen.writeNumberField("size", page.getSize()); // quantidade de elementos por pagina
        gen.writeNumberField("totalElements", page.getTotalElements()); // total de elementos/dados
        gen.writeNumberField("totalPages", page.getTotalPages()); // total de paginas
        gen.writeNumberField("number", page.getNumber()); // pagina atual

        // finalizando um objeto no json
        gen.writeEndObject();
    }

}
