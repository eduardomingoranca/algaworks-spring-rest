function consultar() {
    $.ajax({
        url: "http://127.0.0.1:8080/formas-pagamento",
        type: "get",

        success: function(response) {
            preencherTabela(response);
        }
    });
}

function cadastrar() {
    // transformando o objeto em string
    var formaPagamentoJson = JSON.stringify({
        // obtendo o valor do campo de descricao
        "descricao": $("#campo-descricao").val()
    });
    
    console.log(formaPagamentoJson);

    $.ajax({
        url: "http://127.0.0.1:8080/formas-pagamento",
        type: "post",
        // payload o que sera enviado na requisicao
        data: formaPagamentoJson,
        contentType: "application/json",

        success: function(response) {
            alert("Forma de pagamento adicionada!");
            consultar();
        },

        error: function(error) {
            if (error.status == 400) {
                // convertendo de string para javascript
                var problem = JSON.parse(error.responseText);
                alert(problem.userMessage);
            } else {
                alert("Erro ao cadastrar forma de pagamento!");
            }
        }
    });
}

function preencherTabela(formasPagamento) {
    $("#tabela tbody tr").remove();

    $.each(formasPagamento, function(i, formaPagamento) {
        var linha = $("<tr>");

        linha.append(
            $("<td>").text(formaPagamento.id),
            $("<td>").text(formaPagamento.descricao)
        );

        linha.appendTo("#tabela");
    })
}

$("#btn-consultar").click(consultar);
$("#btn-cadastrar").click(cadastrar);