var select_periodo_aberto = false;
var div_limite_periodo;

$(document).ready(function () {
    busca_moedas();
    div_limite_periodo = $('#div_limite_periodo');
});

function busca_moedas(){
    $.ajax({
        url: 'buscar_moedas',
        async: true,
        type: 'POST',
        dataType: 'json',
        data: {'submit': true},
        success: function(data){
            if(data.isValid) {
                var lista = data.listaMoedas;
                var select = $('#select_moeda');
                $.each(lista, function(i) {
                    select.append($('<option>', {
                        value: lista[i].idMoeda,
                        text: lista[i].nome + ' (' + lista[i].sigla + ')'
                    }));
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

$('#select_periodo').change(function(){
    var opcao = $(this).val();
    var txt_limite = '';
    if(opcao === '0'){
        div_limite_periodo.slideToggle();
        select_periodo_aberto = false;
    } else {
        switch (opcao) {
            case 's':
                txt_limite = 'Semanas';
                break;
            case 'm':
                txt_limite = 'Meses';
                break;
            case 'a':
                txt_limite = 'Anos';
                break
        }
        $('#label_limite_periodo').html('Limite de ' + txt_limite + ' da Pesquisa');

        var select_limite_periodo = $('#select_limite_periodo');
        select_limite_periodo.empty();
        select_limite_periodo.append($('<option>', {
            value: 0,
            text: 'Seleciona uma opção...'
        }));
        for (var i = 1; i < 5; i++) {
            select_limite_periodo.append($('<option>', {
                value: i,
                text: i + ' ' + txt_limite
            }));
        }

        if(!select_periodo_aberto) {
            div_limite_periodo.slideToggle();
            select_periodo_aberto = true;
        }
    }
});

$('#btn_buscar').click(function(){
    $.ajax({
        url: 'historico_moeda',
        async: true,
        type: 'POST',
        dataType: 'json',
        data: {
            'id_moeda': $('#select_moeda').val(),
            'periodo': $('#select_periodo').val(),
            'limite': $('#select_limite_periodo').val()
        },
        success: function(data){
            if(data.isValid) {
                var lista = data.listaMoedas;
                var select = $('#select_moeda');
                $.each(lista, function(i) {
                    select.append($('<option>', {
                        value: lista[i].idMoeda,
                        text: lista[i].nome + ' (' + lista[i].sigla + ')'
                    }));
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
});