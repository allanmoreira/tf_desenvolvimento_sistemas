var select_periodo_aberto = false;
var div_limite_periodo;

$(document).ready(function () {
    busca_moedas();
    div_limite_periodo = $('#div_limite_periodo');
});

function preencheGrafico(tituloGrafico, listaLabels, listaDados) {
    new Chart(document.getElementById("myChart"),{
        type:"line",
        data:{
            labels:listaLabels,
            datasets:[{
                label:tituloGrafico,
                data:listaDados,
                fill:false,
                borderColor:"rgb(75, 192, 192)",
                lineTension:0.1}
            ]},
        options:{}
    });
}

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
    var qtde_limite;
    if(opcao === '0'){
        div_limite_periodo.slideToggle();
        select_periodo_aberto = false;
    } else {
        switch (opcao) {
            case 's':
                txt_limite = 'Semana';
                qtde_limite = 12;
                break;
            case 'm':
                txt_limite = 'Mês';
                qtde_limite = 36;
                break;
            case 'a':
                txt_limite = 'Ano';
                qtde_limite = 3;
                break
        }

        var select_limite_periodo = $('#select_limite_periodo');
        select_limite_periodo.empty();
        select_limite_periodo.append($('<option>', {
            value: 0,
            text: 'Selecione uma opção...'
        }));
        select_limite_periodo.append($('<option>', {
            value: 1,
            text: '1 ' + txt_limite
        }));

        if(opcao === 'm')
            txt_limite = 'Meses';
        else
            txt_limite += 's';

        for (var i = 2; i < qtde_limite+1; i++) {
            select_limite_periodo.append($('<option>', {
                value: i,
                text: i + ' ' + txt_limite
            }));
        }

        if(!select_periodo_aberto) {
            div_limite_periodo.slideToggle();
            select_periodo_aberto = true;
        }

        $('#label_limite_periodo').html('Limite de ' + txt_limite + ' da Pesquisa');
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
                var lista = data.listaPeriodoHistorico;
                console.log(lista);

                var tituloGrafico = 'TESTE';
                var listaLabels = [];
                var listaDados = [];

                $.each(lista, function(i) {
                    listaLabels.push(lista[i].descricao);
                    listaDados.push(lista[i].valor);
                });

                preencheGrafico(tituloGrafico, listaLabels, listaDados);
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