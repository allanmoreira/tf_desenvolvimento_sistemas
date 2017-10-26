
$(document).ready(function () {
    buscar_investimentos();
    $('#lista_menu_superior>li.active').removeClass('active');
    $('#menu_investimentos').addClass('active');
});

function buscar_investimentos(){
    $.ajax({
        url: 'buscar_investimento',
        async: true,
        type: 'POST',
        dataType: 'json',
        data: {'submit': true},
        success: function(data){
            if(data.isValid) {
                var lista = data.listaInvestimentos;

                $.each(lista, function(i) {
                    $('#tabela_investimentos tbody').append(
                        '<tr>' +
                            '<td>'+lista[i].moeda.nome + '(' + lista[i].moeda.sigla +')' +'</td>' +
                            '<td>'+lista[i].descricao+'</td>' +
                            '<td>'+lista[i].dataInicial+'</td>' +
                            '<td>'+lista[i].dataFinal+'</td>' +
                            '<td>'+lista[i].quantidade+'</td>' +
                        '</tr>'
                    );
                });
            }
            else {
                abreNotificacao('warning',data.msgErro);
            }
        },
        error: function (data) {
            abreNotificacao('danger', 'Houve uma falha para realizar a operação! Contate o administrador do sistema!');
        }
    });
}
