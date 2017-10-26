<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%--
    <tiles:insertAttribute name="titulo_aba" ignore="false" />
    <tiles:insertAttribute name="conteudo_pagina" />
    <link rel="stylesheet" href="<c:url value="/static/bootstrap/css/bootstrap.min.css" />">
    <img src="<c:url value="/static/dist/img/avatar.png" />" class="user-image" alt="User Image">
--%>

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

    <title><tiles:insertAttribute name="titulo_aba"/> - Coin Counter</title>

    <!-- Bootstrap core CSS -->
    <link href="<c:url value="/static/css/bootstrap.css" />" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="<c:url value="/static/css/ie10-viewport-bug-workaround.css" />" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="<c:url value="/static/css/jumbotron.css" />" rel="stylesheet">
    <link href="<c:url value="/static/css/layout.css" />" rel="stylesheet">

</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="home">Coin Counter</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="home">Início</a></li>
                <li class="active"><a href="#">Lista de Dados</a></li>
                <li><a href="#">Contato</a></li>
            </ul>
            <ul class="nav navbar-nav pull-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${usuarioLogado.nome} <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Action</a></li>
                        <li><a href="#">Another action</a></li>
                        <li><a href="#">Something else here</a></li>
                        <li><a href="logout">Logout</a></li>
                    </ul>
                </li>
            </ul>
        </div><!--/.navbar-collapse -->
    </div>
</nav>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="<c:url value="/static/js/jquery-3.2.1.min.js"/>"></script>
<script src="<c:url value="/static/js/bootstrap.min.js"/>"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="<c:url value="/static/js/ie10-viewport-bug-workaround.js"/>"></script>
<script src="<c:url value="/static/plugins/bootstrap-notify.js"/>"></script>
<script src="<c:url value="/static/plugins/notificacao.js"/>"></script>
<script src="<c:url value="/static/plugins/loading_ajax.js"/>"></script>
<script src="<c:url value="/static/plugins/Chart.bundle.js"/>"></script>

<div class="container">
    <div class="row">
        <h2 class="sub-header"><tiles:insertAttribute name="titulo_pagina" /></h2>
        <h4 class="subtitulo"><tiles:insertAttribute name="sub_titulo_pagina" /></h4>
    </div>

    <tiles:insertAttribute name="conteudo_pagina" />

    <hr>

    <footer>
        <p>&copy; 2016 Company, Inc.</p>
    </footer>
</div> <!-- /container -->



</body>
</html>
