var select_periodo_aberto = false;
var div_limite_periodo;

$(document).ready(function () {
    busca_moedas();
    div_limite_periodo = $('#div_limite_periodo');
    preencheGrafico();
});

function preencheGrafico() {
    var chartClassic    = $('#chart-classic');
    var dataEarnings    = [[1, 1900], [2, 2300], [3, 3200], [4, 2500], [5, 4200], [6, 3100], [7, 3600], [8, 2500], [9, 4600], [10, 3700], [11, 4200], [12, 5200]];
    var dataSales       = [[1, 850], [2, 750], [3, 1500], [4, 900], [5, 1500], [6, 1150], [7, 1500], [8, 900], [9, 1800], [10, 1700], [11, 1900], [12, 2550]];
    var dataTickets     = [[1, 130], [2, 330], [3, 220], [4, 350], [5, 150], [6, 275], [7, 280], [8, 380], [9, 120], [10, 330], [11, 190], [12, 410]];
    var dataMonths      = [[1, 'Jan'], [2, 'Feb'], [3, 'Mar'], [4, 'Apr'], [5, 'May'], [6, 'Jun'], [7, 'Jul'], [8, 'Aug'], [9, 'Sep'], [10, 'Oct'], [11, 'Nov'], [12, 'Dec']];
    $.plot=function(placeholder,data,options){
        var plot=new Plot($(placeholder),data,options,$.plot.plugins);
        return plot
    };

    $.plot.version="0.8.3";
    $.plot.plugins=[];
    $.fn.plot=function(data,options){
        return this.each(function(){
            $.plot(this,data,options)
        })
    };

    // function floorInBase(n,base){return base*Math.floor(n/base)}})(jQuery);

    // Classic Chart
    $.plot(chartClassic,
        [
            {
                label: 'Earnings',
                data: dataEarnings,
                lines: {show: true, fill: true, fillColor: {colors: [{opacity: .6}, {opacity: .6}]}},
                points: {show: true, radius: 5}
            },
            {
                label: 'Sales',
                data: dataSales,
                lines: {show: true, fill: true, fillColor: {colors: [{opacity: .2}, {opacity: .2}]}},
                points: {show: true, radius: 5}
            },
            {
                label: 'Tickets',
                data: dataTickets,
                lines: {show: true, fill: true, fillColor: {colors: [{opacity: .2}, {opacity: .2}]}},
                points: {show: true, radius: 5}
            }
        ],
        {
            colors: ['#5ccdde', '#454e59', '#ffffff'],
            legend: {show: true, position: 'nw', backgroundOpacity: 0},
            grid: {borderWidth: 0, hoverable: true, clickable: true},
            yaxis: {tickColor: '#f5f5f5', ticks: 3},
            xaxis: {ticks: dataMonths, tickColor: '#f5f5f5'}
        }
    );
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
                var lista = data.listaHistoricoMoeda;
                console.log(lista);
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