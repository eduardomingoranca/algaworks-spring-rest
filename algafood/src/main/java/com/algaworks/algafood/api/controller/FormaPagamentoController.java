package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

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
    public List<FormaPagamentoModel> listar() {
        List<FormaPagamento> formasPagamento = cadastroFormaPagamento.listar();

        return formaPagamentoModelAssembler.toCollectionModel(formasPagamento);
    }

    @GetMapping("/{formasPagamentoId}")
    public FormaPagamentoModel buscar(@PathVariable("formasPagamentoId") Long id) {
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(id);

        return formaPagamentoModelAssembler.toModel(formaPagamento);
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
