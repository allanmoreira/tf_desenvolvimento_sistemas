$('#email, #senha').keydown(function (e){
    if(e.keyCode == 13)
        enviaFormulario()
});

$('#btn_logar').click(function(){
    enviaFormulario();
});

$('#btn_login_google').click(function () {
    buscaDadosFirebase('google');
});

$('#btn_login_facebook').click(function () {
    buscaDadosFirebase('facebook');
});

function enviaFormulario() {
    var email = $('#email').val();
    var senha = $('#senha').val();

    if(!validaEmail(email)){
        abreNotificacao('warning', 'Informe um email válido!');
    } else {
        $.ajax({
            url: 'efetuar_login',
            async: true,
            type: 'POST',
            dataType: 'json',
            data: {email: email, senha: senha},
            success: function (data) {
                if (data.isValid) {
                    abreNotificacao('success', 'Login efetuado com sucesso!');
                    setTimeout(function () {
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
}


