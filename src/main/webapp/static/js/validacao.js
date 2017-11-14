function validaEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

function validaNomeUsuario(nome) {
    console.log(nome.split(' ').length > 1);
    return nome.split(' ').length > 1;
}

function primeiraLetraMaiuscula(text) {
    var words = text.toLowerCase().split(" ");
    for (var a = 0; a < words.length; a++) {
        var w = words[a];
        words[a] = w[0].toUpperCase() + w.slice(1);
    }
    return words.join(" ");
}

function senhaValida(senha) {
    var minCarac = 5;
    if (senha.length < minCarac) {
        abreNotificacao('warning', 'A senha deve ter pelo menos ' + minCarac + ' caracteres!');
        return false;
    }else if(senha.search(/\d/) === -1 || senha.search(/[a-zA-Z]/) === -1) {
        abreNotificacao('warning', 'A senha deve letras e números!');
        return false;
    }else if(senha.search(/[A-Z]/) === -1) {
        abreNotificacao('warning', 'A senha deve pelo menos uma letra maiúscula!');
        return false;
    }
    return true;
}