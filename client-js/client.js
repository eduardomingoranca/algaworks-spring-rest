function consultarRestaurantes() {
    // realizando uma chamada http de forma assincrona
    // ou seja, nao bloqueia o navegador enquando essa 
    // chamada esta acontecendo.
    $.ajax({
        // url da requisicao
        url: "http://127.0.0.1:8080/restaurantes",
        // tipo do verbo da requisicao
        type: "get", 

        // em caso de sucesso na requisicao executa a funcao retornando o conteudo 
        success: function(response) {
            $("#conteudo").text(response);
        }
    });
}

// ao clicar no batao executa o metodo
$("#botao").click(consultarRestaurantes);