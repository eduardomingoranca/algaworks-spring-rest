package com.algaworks.algafood.api.assembler.model;

import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.links.AlgaLinks;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }

    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        // mapeando a origem/entrada e o objeto de destino model.
        modelMapper.map(restaurante, restauranteModel);

        restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));

        restauranteModel.getCozinha().add(algaLinks.linkToCozinha(restauranteModel.getId()));
        restauranteModel.getEndereco().getCidade().add(algaLinks.linkToCidade(restauranteModel.getId()));

        restauranteModel.add(algaLinks.linkToFormaPagamentoRestaurante(restauranteModel.getId(), "formas-pagamento"));
        restauranteModel.add(algaLinks.linkToResponsaveisRestaurante(restauranteModel.getId(), "responsaveis"));

        if (podeSerAtivado(restaurante))
            restauranteModel.add(algaLinks.linkToAtivacaoRestaurante(restaurante.getId(), "ativar"));

        if (podeSerInativado(restaurante))
            restauranteModel.add(algaLinks.linkToInativacaoRestaurante(restaurante.getId(), "inativar"));

        if (podeSerAberto(restaurante))
            restauranteModel.add(algaLinks.linkToAberturaRestaurante(restaurante.getId(), "abrir"));

        if (podeSerFechado(restaurante))
            restauranteModel.add(algaLinks.linkToFechamentoRestaurante(restaurante.getId(), "fechar"));

        return restauranteModel;
    }

    private boolean podeSerFechado(Restaurante restaurante) {
        return restaurante.getAberto().equals(TRUE);
    }

    private boolean podeSerAberto(Restaurante restaurante) {
        return restaurante.getAberto().equals(FALSE);
    }

    private boolean podeSerInativado(Restaurante restaurante) {
        return restaurante.getAtivo().equals(TRUE);
    }

    private boolean podeSerAtivado(Restaurante restaurante) {
        return restaurante.getAtivo().equals(FALSE);
    }

}
