<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="row">
    <div class="col-sm-12">
        <!-- Classic Chart Block -->
        <div class="block full">
            <table class="table table-hover" id="tabela_investimentos">
                <thead>
                <tr>
                    <th>Moeda</th>
                    <th>Descrição</th>
                    <th>Data Inicial</th>
                    <th>Data Final</th>
                    <th>Quantidade</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>
        <!-- END Classic Chart Block -->
    </div>

</div>

<br>

<script src="<c:url value="/static/js_paginas/investimentos.js"/>"></script>



