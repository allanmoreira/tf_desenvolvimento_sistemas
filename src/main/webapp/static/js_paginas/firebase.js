var nome, email;

// Initialize Firebase
var config = {
    apiKey: "AIzaSyA3jlPUTJ6ZneELCJbVnVav6ZwAiR4G6Xo",
    authDomain: "trabalho-desenv-sistemas.firebaseapp.com",
    databaseURL: "https://trabalho-desenv-sistemas.firebaseio.com",
    projectId: "trabalho-desenv-sistemas",
    storageBucket: "",
    messagingSenderId: "536064664804"
};
firebase.initializeApp(config);

function buscaDadosFirebase(tipo) {
    var provider;
    var msgErro = '';

    if(tipo === 'google') {
        provider = new firebase.auth.GoogleAuthProvider();
        msgErro = 'Houve um erro ao conectar a conta do Google';
    } else if(tipo === 'facebook') {
        provider = new firebase.auth.FacebookAuthProvider();
        msgErro = 'Houve um erro ao conectar a conta do Facebook';
    }
    firebase.auth().signInWithPopup(provider).then(function(result) {
        var user = result.user;
        console.log(user);

        if(tipo === 'google') {
            nome = user.displayName;
            email = user.email;
        } else if(tipo === 'facebook') {
            nome = user.providerData[0].displayName;
            email = user.providerData[0].email;
        }

        loginFirebase();
    }).catch(function(error) {
        console.log(error);
        abreNotificacao('danger', msgErro);
    });

}

function loginFirebase() {
    $.ajax({
        url: 'login_usuario_firebase',
        async: true,
        type: 'POST',
        dataType: 'json',
        data: {nome:nome, email:email},
        success: function(data){
            if(data.isValid) {
                abreNotificacao('success', 'Login efetuado com sucesso!');
                setTimeout(function(){
                    window.location = 'home';
                }, 1000);
            }
            else {
                abreNotificacao('warning', data.msgErro);
            }
        },
        error: function (data) {
            console.log(data.responseText);
            abreNotificacao('danger', 'Houve uma falha para realizar a operação! Contate o administrador do sistema!');
        }
    });
}

