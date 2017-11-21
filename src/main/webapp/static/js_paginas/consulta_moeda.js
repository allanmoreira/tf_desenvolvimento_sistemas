var select_periodo_aberto = false;
var btn_invest_visivel = false;
var div_limite_periodo;
var mediaVolumeMoeda;
var mediafechamentoMoeda;
var div_sugestao_aberta = false;
var listaDadosFechamento = [];
var listaDadosVolume = [];
var idMoeda;
var descricao;
var dataInicial;
var dataFinal;
var quantidade;

$(document).ready(function () {
    $('#lista_menu_superior>li.active').removeClass('active');
    $('#menu_consulta_moeda').addClass('active');
    busca_moedas();
    div_limite_periodo = $('#div_limite_periodo');
    $('#data_inicial').mask('99/99/9999');
    $('#data_final').mask('99/99/9999');
    $('#quantidade').numeric({ altDecimal: ".", decimal : "," });
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
                var select_moeda_investimento = $('#select_moeda_investimento');
                $.each(lista, function(i) {
                    select.append($('<option>', {
                        value: lista[i].idMoeda,
                        text: lista[i].nome + ' (' + lista[i].sigla + ')'
                    }));

                    select_moeda_investimento.append($('<option>', {
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
    var select_limite_periodo = $('#select_limite_periodo');

    if(div_sugestao_aberta){
        $('#div_sugestao').slideToggle();
        div_sugestao_aberta = false;
    }

    if(select_moeda.val() === '0'){
        abreNotificacao('warning', 'Informe a moeda!');
    } else if(select_periodo.val() === '0'){
        abreNotificacao('warning', 'Informe o período!');
    } else if(select_limite_periodo.val() === '0'){
        abreNotificacao('warning', 'Informe o limite do período!');
    } else {
        var btn_modal_investimento = $('#btn_abre_modal_investimento');
        $.ajax({
            url: 'historico_moeda',
            async: true,
            type: 'POST',
            dataType: 'json',
            data: {
                'id_moeda': select_moeda.val(),
                'periodo': select_periodo.val(),
                'limite': select_limite_periodo.val()
            },
            success: function (data) {
                if (data.isValid) {
                    idMoeda = select_moeda.val();
                    var lista = data.listaPeriodoHistorico;
                    var nomeMoeda = select_moeda.find(":selected").text();
                    var txtPeriodo = select_periodo.find(":selected").text();
                    $('#sub_titulo_pagina').html(nomeMoeda);
                    $('#moeda_selecionada').val(nomeMoeda);
                    $('#titulo_modal_investimento').html('Investimento - ' + nomeMoeda);
                    var listaLabels = [];
                    listaDadosFechamento = [];
                    listaDadosVolume = [];
                    var somatorioVolume = 0;
                    var somatorioFechamento = 0;

                    $.each(lista, function (i) {
                        somatorioVolume += lista[i].valorVolume;
                        somatorioFechamento += lista[i].valorFechamento;
                        listaLabels.push(lista[i].descricao);
                        listaDadosFechamento.push(lista[i].valorFechamento);
                        listaDadosVolume.push(lista[i].valorVolume);
                    });

                    mediaVolumeMoeda = somatorioVolume / lista.length;
                    mediafechamentoMoeda = somatorioFechamento / lista.length;

                    $('#modal_consulta').modal('hide');

                    if (!btn_invest_visivel) {
                        btn_modal_investimento.slideToggle();
                        btn_invest_visivel = true;
                    }

                    $('#div_grafico_fechamento').html('<canvas id="canvas_grafico_fechamento" ></canvas>');
                    $('#div_grafico_volume').html('<canvas id="canvas_grafico_volume" ></canvas>');
                    preencheGrafico(nomeMoeda, listaLabels, 'Fechamento', listaDadosFechamento, 'canvas_grafico_fechamento', 'rgb(75, 192, 192)', txtPeriodo);
                    preencheGrafico(nomeMoeda, listaLabels, 'Volume', listaDadosVolume, 'canvas_grafico_volume', 'rgb(220, 47, 47)', txtPeriodo);

                    $('#texto_sugestao').html('O melhor momento para investir em <strong>' + nomeMoeda + '</strong> é quando a moeda estiver com quantidade no mínimo de <strong>' + mediafechamentoMoeda + '</strong> para o período.');
                    $('#div_sugestao').slideToggle();
                    div_sugestao_aberta = true;
                }
                else {
                    abreNotificacao('warning', data.msgErro);
                }
            },
            error: function (data) {
                abreNotificacao('danger', 'Houve uma falha para realizar a operação! Contate o administrador do sistema!');
            }
        });
    }
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

$('#btn_salvar_investimento').click(function(){
    pegaDadosInvestimento();
    $.ajax({
        url: 'novo_investimento',
        async: true,
        type: 'POST',
        dataType: 'json',
        data: {
            'id_moeda': idMoeda,
            'descricao': descricao,
            'data_inicial': dataInicial,
            'data_final': dataFinal,
            'quantidade': quantidade
        },
        success: function(data){
            if(data.isValid) {
                abreNotificacao('success','Investimento salvo com sucesso!');
            } else {
                abreNotificacao('warning',data.msgErro);
            }
        },
        error: function (data) {
            abreNotificacao('danger', 'Houve uma falha para realizar a operação! Contate o administrador do sistema!');
        }
    });
});
/*
$('#btn_simular_investimento').click(function(){
    pegaDadosInvestimento();
    $.ajax({
        url: 'simular_investimento',
        async: true,
        type: 'POST',
        dataType: 'json',
        data: {
            'id_moeda': idMoeda,
            'descricao': descricao,
            'data_inicial': dataInicial,
            'data_final': dataFinal,
            'quantidade': quantidade
        },
        success: function(data){
            if(data.isValid) {
                console.log(data);
            } else {
                abreNotificacao('warning',data.msgErro);
            }
        },
        error: function (data) {
            abreNotificacao('danger', 'Houve uma falha para realizar a operação! Contate o administrador do sistema!');
        }
    });
});
*/
function pegaDadosInvestimento() {
    descricao = $('#descricao').val();
    dataInicial = $('#data_inicial').val();
    dataFinal = $('#data_final').val();
    quantidade = $('#quantidade').val();
}