package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.input.disassembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.assembler.model.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;

import static java.lang.String.valueOf;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.springframework.http.CacheControl.maxAge;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.filter.ShallowEtagHeaderFilter.disableContentCaching;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {
    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;

    @Autowired
    private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;


    @GetMapping
    public ResponseEntity<List<FormaPagamentoModel>> listar(ServletWebRequest request) {
        /*
            gerando um hash de um atributo especifico, desde que o atributo tenha alteracoes
            quando a entidade eh atualizada,
            como por exemplo uma data e hora.
         */

        // desabilitando o shallow etag
        disableContentCaching(request.getRequest());

        String eTag = "0";
        OffsetDateTime dataUltimaAtualizacao = cadastroFormaPagamento.dataUltimaAtualizacao();

        if (dataUltimaAtualizacao != null) {
            // obtendo o etag com o numero de segundos.
            eTag = valueOf(dataUltimaAtualizacao.toEpochSecond());
        }

        // ja tem condicao de saber se continua ou nao o processamento
        if (request.checkNotModified(eTag))
            return null;

        List<FormaPagamento> formasPagamento = cadastroFormaPagamento.listar();

        List<FormaPagamentoModel> formasPagamentoModel = formaPagamentoModelAssembler.toCollectionModel(formasPagamento);

        return ok()
                // resposta armazenada em cache publico
                .cacheControl(maxAge(10, SECONDS).cachePublic())
                .eTag(eTag)
                .body(formasPagamentoModel);
    }

    @GetMapping("/{formasPagamentoId}")
    public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable("formasPagamentoId") Long id) {
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(id);

        FormaPagamentoModel formaPagamentoModel = formaPagamentoModelAssembler.toModel(formaPagamento);

        return ok()
                .cacheControl(maxAge(10, SECONDS))
                .body(formaPagamentoModel);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);
        FormaPagamento salvarFormaPagamento = cadastroFormaPagamento.salvar(formaPagamento);

        return formaPagamentoModelAssembler.toModel(salvarFormaPagamento);
    }

    @PutMapping("/{formasPagamentoId}")
    public FormaPagamentoModel atualizar(@PathVariable("formasPagamentoId") Long id,
                                         @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(id);

        formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamento);

        FormaPagamento salvarFormaPagamento = cadastroFormaPagamento.salvar(formaPagamento);

        return formaPagamentoModelAssembler.toModel(salvarFormaPagamento);
    }

}
