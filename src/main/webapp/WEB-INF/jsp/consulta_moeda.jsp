<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="row">
    <br><button type="button" class="btn btn-primary" id="btn_abre_modal_consulta"
                data-toggle="modal" data-target="#modal_consulta" disabled>Nova Consulta</button>

    <button type="button" class="btn btn-success" id="btn_abre_modal_investimento" style="display: none"
                data-toggle="modal" data-target="#modal_investir">Investir</button><br><br>
</div>

<div class="row" id="div_sugestao" style="display: none;">
    <div class="col-sm-12 col-md-offset-1">
        <h5 id="texto_sugestao"></h5>
    </div>
</div>

<div class="row">
    <div class="col-sm-6">
        <!-- Classic Chart Block -->
        <div class="block full">
            <!-- Classic Chart Content -->
            <!-- Flot Charts (initialized in js/pages/compCharts.js), for more examples you can check out http://www.flotcharts.org/ -->
            <div id="div_grafico_fechamento" style="height: 300px;">
                <canvas id="canvas_grafico_fechamento" ></canvas>
            </div>
            <!-- END Classic Chart Content -->
        </div>
        <!-- END Classic Chart Block -->
    </div>

    <div class="col-sm-6">
        <!-- Classic Chart Block -->
        <div class="block full">
            <!-- Classic Chart Content -->
            <!-- Flot Charts (initialized in js/pages/compCharts.js), for more examples you can check out http://www.flotcharts.org/ -->
            <div id="div_grafico_volume" style="height: 300px;">
                <canvas id="canvas_grafico_volume" ></canvas>
            </div>
            <!-- END Classic Chart Content -->
        </div>
        <!-- END Classic Chart Block -->
    </div>
</div>

<br>

<!-- Modal -->
<div class="modal fade" id="modal_consulta" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Buscar Moeda</h4>
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

<!-- Modal -->
<div class="modal fade" id="modal_investir" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="titulo_modal_investimento"></h4>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label class="control-label">Moeda</label>
                        <input class="form-control disabled" id="moeda_selecionada" disabled/>
                    </div>
                    <div class="form-group">
                        <label class="control-label">Descrição</label>
                        <textarea class="form-control" rows="3" id="descricao"></textarea>
                    </div>
                    <div class="form-group">
                        <label class="control-label" >Data Inicial</label>
                        <input class="form-control" id="data_inicial"/>
                    </div>
                    <div class="form-group">
                        <label class="control-label" >Data Final</label>
                        <input class="form-control" id="data_final"/>
                    </div>
                    <div class="form-group">
                        <label class="control-label" >Quantidade</label>
                        <input class="form-control" id="quantidade"/>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success pull-left" id="btn_simular_investimento">Simular</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
                <button type="button" class="btn btn-primary" id="btn_salvar_investimento">Salvar</button>
            </div>
        </div>
    </div>
</div>
<!-- Modal -->

<script src="<c:url value="/static/js_paginas/consulta_moeda.js"/>"></script>



