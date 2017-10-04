$('#nome, #email, #senha').keydown(function (e){
    if(e.keyCode == 13)
        enviaFormulario()
});

$('#btn_registrar').click(function(){
    enviaFormulario();
});

function enviaFormulario() {
    var nome = $('#nome').val();
    var email = $('#email').val();
    var senha = $('#senha').val();

    $.ajax({
        url: 'registrar_usuario',
        async: true,
        type: 'POST',
        dataType: 'json',
        data: {nome: nome, email:email, senha:senha},
        success: function(data){
            if(data.isValid) {
                abreNotificacao('success', 'Registro efetuado com sucesso!');
                setTimeout(function(){
                    window.location = 'home';
                }, 1000);

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