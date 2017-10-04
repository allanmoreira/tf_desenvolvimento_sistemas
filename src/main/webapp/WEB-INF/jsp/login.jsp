<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="<c:url value="/static/css/favicon.ico" />">

    <title>Login - Coin Counter</title>
    <!-- Bootstrap core CSS -->
    <link href="<c:url value="/static/css/bootstrap.css" />" rel="stylesheet">
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="<c:url value="/static/css/ie10-viewport-bug-workaround.css" />" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="<c:url value="/static/css/signin.css" />" rel="stylesheet">
</head>

<body>

<div class="container">

    <form class="form-signin">
        <h2 class="form-signin-heading">Informe seu Login</h2>
        <label class="sr-only">Email</label>
        <input type="email" id="email" class="form-control" placeholder="Email" required autofocus>
        <label class="sr-only">Senha</label>
        <input type="password" id="senha" class="form-control" placeholder="Senha" required>
        <div class="checkbox">
            <label>
                <input type="checkbox" value="remember-me"> Lembrar meu usuário e senha
            </label>

        </div>
        <label><a href="registro">Não possui conta? Cadastre-se</a></label>
        <button class="btn btn-lg btn-primary btn-block" id="btn_logar" type="button">Login</button>
    </form>

</div> <!-- /container -->


<script src="<c:url value="/static/js/jquery-3.2.1.min.js"/>"></script>
<script src="<c:url value="/static/js/ie10-viewport-bug-workaround.js"/>"></script>
<script src="<c:url value="/static/plugins/bootstrap-notify.js"/>"></script>
<script src="<c:url value="/static/plugins/notificacao.js"/>"></script>
<script src="<c:url value="/static/plugins/loading_ajax.js"/>"></script>
<script src="<c:url value="/static/js_paginas/login.js"/>"></script>
</body>
</html>