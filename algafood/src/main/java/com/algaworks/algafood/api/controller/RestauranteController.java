package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.springframework.beans.BeanUtils.copyProperties;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.util.ReflectionUtils.*;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @GetMapping
    public ResponseEntity<List<Restaurante>> listar() {
        List<Restaurante> restaurantes = cadastroRestaurante.listar();

        return status(OK).body(restaurantes);
    }

    @GetMapping("/{restauranteId}")
    public Restaurante buscar(@PathVariable("restauranteId") Long id) {
        return cadastroRestaurante.buscarOuFalhar(id);
    }

    @PostMapping
    public Restaurante adicionar(@RequestBody Restaurante restaurante) {
        return cadastroRestaurante.salvar(restaurante);
    }

    @PutMapping("/{restauranteId}")
    public Restaurante atualizar(@PathVariable("restauranteId") Long id, @RequestBody Restaurante restaurante) {
        Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);
        copyProperties(restaurante, restauranteAtual, "id", "formasPagamento",
                "endereco", "dataCadastro", "produtos");

        return cadastroRestaurante.salvar(restauranteAtual);
    }

    @PatchMapping("/{restauranteId}")
    public Restaurante atualizarParcial(@PathVariable("restauranteId") Long id, @RequestBody Map<String, Object> campos) {
        Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);
        merge(campos, restauranteAtual);

        return atualizar(id, restauranteAtual);
    }

//     mesclar os valores do map campos para dentro do restaurante atual
    private static void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
//         ObjectMapper -> class responsavel por converter objetos java em json e json em objetos java
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
//             obtendo os campos de restaurante e informado no nome da propriedade informada
            Field field = findField(Restaurante.class, nomePropriedade);
//             acessando um atributo/metodo private
            assert field != null;
            field.setAccessible(true);

//             obtendo o valor da propriedade convertido
            Object novoValor = getField(field, restauranteOrigem);

//             alterando o valor da variavel de instancia informada pelo valor da propriedade
            setField(field, restauranteDestino, novoValor);
        });
    }

}
