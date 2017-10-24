<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="row">
    <br><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modal_consulta">Nova Consulta</button><br><br>
</div>

<div class="row">
    <div class="table-responsive">
        <table class="table table-striped" id="tabela_moedas">
            <thead>
            <tr>
                <th>#</th>
                <th>Header</th>
                <th>Header</th>
                <th>Header</th>
                <th>Header</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>1,001</td>
                <td>Lorem</td>
                <td>ipsum</td>
                <td>dolor</td>
                <td>sit</td>
            </tr>
            <tr>
                <td>1,002</td>
                <td>amet</td>
                <td>consectetur</td>
                <td>adipiscing</td>
                <td>elit</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="modal_consulta" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Novo Dado</h4>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label class="control-label">Moeda</label>
                        <select class="form-control" id="select_moeda">
                            <option value="0">Selecione uma opção...</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="control-label">Período da Pesquisa</label>
                        <select class="form-control" id="select_periodo">
                            <option value="0">Selecione uma opção...</option>
                            <option value="s">Semana</option>
                            <option value="m">Mês</option>
                            <option value="a">Ano</option>
                        </select>
                    </div>
                    <div class="form-group" id="div_limite_periodo" style="display: none">
                        <label class="control-label" id="label_limite_periodo">Limite</label>
                        <select class="form-control" id="select_limite_periodo">
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
                <button type="button" class="btn btn-primary" id="btn_buscar">Buscar</button>
            </div>
        </div>
    </div>
</div>
<!-- Modal -->

<script src="<c:url value="/static/js_paginas/consulta_moeda.js"/>"></script>



