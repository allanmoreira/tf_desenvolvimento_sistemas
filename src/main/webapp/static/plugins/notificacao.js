/**
 * Creat1ed by allan.moreira on 15/02/2017.
 */


function abreNotificacao(tipo, msg, manterAberto) {
    var tempoDelay = 1000;
    if(manterAberto)
        tempoDelay = 0;

    var icone;
    switch (tipo) {
        case 'success':
            icone = 'glyphicon glyphicon-ok-sign';
            break;
        case 'danger':
            icone = 'glyphicon glyphicon-warning-sign';
            break;
        default:
            icone = 'glyphicon glyphicon-info-sign';
    }

    var notificacao =
        $.notify({
            // options
            icon: icone,
            message: msg,
        }, {
            // settings
            element: 'body',
            position: null,
            type: tipo,
            allow_dismiss: true,
            newest_on_top: false,
            showProgressbar: false,
            placement: {
                from: "bottom",
                align: "left"
            },
            offset: 20,
            spacing: 10,
            z_index: 3000,
            delay: tempoDelay,
            // timer: 1,
            animate: {
                enter: 'animated fadeInDown',
                exit: 'animated fadeOutUp'
            },
            icon_type: 'class'

        });

    return notificacao;
}

function fechaNotificacao(notificacao) {
    notificacao.close();
}