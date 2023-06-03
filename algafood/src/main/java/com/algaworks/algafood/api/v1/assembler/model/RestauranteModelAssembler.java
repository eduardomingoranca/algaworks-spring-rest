package com.algaworks.algafood.api.v1.assembler.model;

import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

    @Autowired
    private AlgaSecurity algaSecurity;

    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }

    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        // mapeando a origem/entrada e o objeto de destino model.
        modelMapper.map(restaurante, restauranteModel);

        if (algaSecurity.podeConsultarRestaurantes())
            restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));

        if (algaSecurity.podeConsultarCozinhas())
            restauranteModel.getCozinha().add(algaLinks.linkToCozinha(restaurante.getCozinha().getId()));

        if (algaSecurity.podeConsultarCidades()) {
            if (restauranteModel.getEndereco() != null && restauranteModel.getEndereco().getCidade() != null)
                restauranteModel.getEndereco().getCidade().add(algaLinks.linkToCidade(restaurante.getEndereco()
                        .getCidade().getId()));
        }

        if (algaSecurity.podeConsultarRestaurantes())
            restauranteModel.add(algaLinks.linkToFormaPagamentoRestaurante(restaurante.getId(),
                    "formas-pagamento"));

        if (algaSecurity.podeGerenciarCadastroRestaurantes())
            restauranteModel.add(algaLinks.linkToResponsaveisRestaurante(restaurante.getId(),
                    "responsaveis"));

        if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
            if (podeSerAtivado(restaurante))
                restauranteModel.add(algaLinks.linkToAtivacaoRestaurante(restaurante.getId(), "ativar"));

            if (podeSerInativado(restaurante))
                restauranteModel.add(algaLinks.linkToInativacaoRestaurante(restaurante.getId(), "inativar"));
        }

        if (algaSecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {
            if (podeSerAberto(restaurante))
                restauranteModel.add(algaLinks.linkToAberturaRestaurante(restaurante.getId(), "abrir"));

            if (podeSerFechado(restaurante))
                restauranteModel.add(algaLinks.linkToFechamentoRestaurante(restaurante.getId(), "fechar"));
        }

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        CollectionModel<RestauranteModel> collectionModel = super.toCollectionModel(entities);

        if (algaSecurity.podeConsultarRestaurantes())
            collectionModel.add(algaLinks.linkToRestaurantes());

        return collectionModel;
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
