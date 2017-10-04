/**
 * Created by allan on 20/03/17.
 */
var notificacao;
$(document).ajaxStart(function(){
    notificacao = abreNotificacao('info', 'Executando a operação, aguarde...', true);
}).ajaxStop(function(){
    fechaNotificacao(notificacao);
});
