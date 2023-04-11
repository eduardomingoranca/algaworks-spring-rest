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

import javax.validation.Valid;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.springframework.http.CacheControl.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;

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
    public ResponseEntity<List<FormaPagamentoModel>> listar() {
        List<FormaPagamento> formasPagamento = cadastroFormaPagamento.listar();

        List<FormaPagamentoModel> formasPagamentoModel = formaPagamentoModelAssembler.toCollectionModel(formasPagamento);

        return ok()
                // Cache-Control: max-age = 10
//                .cacheControl(maxAge(10, SECONDS))
//                .cacheControl(maxAge(10, SECONDS).cachePrivate()) // resposta armazenada em cache local
//                .cacheControl(maxAge(10, SECONDS).cachePublic()) // resposta armazenada em cache publico
//                .cacheControl(noCache()) // se a reposta for armazenada em cache sera necessario realizar uma validacao no servidor
                .cacheControl(noStore()) // nenhum cache pode armazenar a resposta
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
