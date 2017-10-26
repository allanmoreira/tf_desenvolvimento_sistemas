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
                $('#btn_abre_modal_consulta').attr('disabled', false);
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
    var select_moeda = $('#select_moeda');
    var select_periodo = $('#select_periodo');
    $.ajax({
        url: 'historico_moeda',
        async: true,
        type: 'POST',
        dataType: 'json',
        data: {
            'id_moeda': select_moeda.val(),
            'periodo': select_periodo.val(),
            'limite': $('#select_limite_periodo').val()
        },
        success: function(data){
            if(data.isValid) {
                var lista = data.listaPeriodoHistorico;
                var nomeMoeda = select_moeda.find(":selected").text();
                var txtPeriodo = select_periodo.find(":selected").text();
                var listaLabels = [];
                var listaDadosFechamento = [];
                var listaDadosVolume = [];

                $.each(lista, function(i) {
                    listaLabels.push(lista[i].descricao);
                    listaDadosFechamento.push(lista[i].valorFechamento);
                    listaDadosVolume.push(lista[i].valorVolume);
                });
                $('#modal_consulta').modal('hide');
                $('#div_grafico_fechamento').html('<canvas id="canvas_grafico_fechamento" ></canvas>');
                $('#div_grafico_volume').html('<canvas id="canvas_grafico_volume" ></canvas>');
                preencheGrafico(nomeMoeda, listaLabels, 'Fechamento', listaDadosFechamento, 'canvas_grafico_fechamento', 'rgb(75, 192, 192)', txtPeriodo);
                preencheGrafico(nomeMoeda, listaLabels, 'Volume', listaDadosVolume, 'canvas_grafico_volume', 'rgb(220, 47, 47)', txtPeriodo);
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

function preencheGrafico(nomeMoeda, listaLabels, txtDescricao, listaDados, id_grafico, rgb_cor, txtPeriodo) {
    new Chart(document.getElementById(id_grafico),{
        type:"line",
        data:{
            labels:listaLabels,
            datasets:[
                {
                    label:txtDescricao,
                    data:listaDados,
                    fill:false,
                    borderColor:rgb_cor,
                    lineTension:0.1
                }
            ]
        },
        options:{
            responsive: true,
            title:{
                display:true,
                text:nomeMoeda
            },
            tooltips: {
                mode: 'index',
                intersect: false
            },
            hover: {
                mode: 'nearest',
                intersect: true
            },
            scales: {
                xAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: txtPeriodo
                    }
                }],
                yAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: 'Valor'
                    }
                }]
            }
        }
    });
}
