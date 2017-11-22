package regraNegocio;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import persistencia.dao.InvestimentoDAO;
import persistencia.dao.MoedaDAO;
import persistencia.modelos.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class InvestimentoControle {

    private InvestimentoDAO investimentoDAO;

    public InvestimentoControle() {
        this.investimentoDAO = new InvestimentoDAO();
    }

    public void insereInvestimento(String idMoeda, Usuario usuario, String descricao, String dataInicial, String dataFinal, String quantidade) throws ValidacaoException, BDException {
        validaDados(true, idMoeda, descricao, dataInicial, dataFinal, quantidade);
        int idMoedaInt = Integer.valueOf(idMoeda);
        SimpleDateFormat formatoDataParam = new SimpleDateFormat("dd/MM/yyyy");
        Date dataInicialDate;
        Date dataFinalDate;

        if(descricao == null || descricao.equals(""))
            throw new ValidacaoException("Informe a descrição!");

        try {
            dataInicialDate = new Date(formatoDataParam.parse(dataInicial).getTime());
            dataFinalDate = new Date(formatoDataParam.parse(dataInicial).getTime());
        } catch (ParseException e) {
            throw new ValidacaoException("Informe a data inicial e final!");
        }

        if(quantidade == null || quantidade.equals(""))
            throw new ValidacaoException("Informe a quantidade!");

        BigDecimal quantidadeBigDecimal = new BigDecimal(quantidade.replace(',', '.'));

        Investimento investimento = new Investimento(new Moeda(idMoedaInt), usuario, descricao, dataInicialDate, dataFinalDate, quantidadeBigDecimal);
        investimentoDAO.insert(investimento);
    }

    public Simulacaoinvestimento simulaInvestimento(String idMoeda, Usuario usuario, String descricao, String dataInicial, String dataFinal, String quantidade) throws ValidacaoException, BDException, ParseException {
        validaDados(false, idMoeda, descricao, dataInicial, dataFinal, quantidade);
        int idMoedaInt = Integer.valueOf(idMoeda);
        SimpleDateFormat formatoDataParam = new SimpleDateFormat("dd/MM/yyyy");
        Date dataInicialDate;
        Date dataFinalDate;

        try {
            dataInicialDate = new Date(formatoDataParam.parse(dataInicial).getTime());
            dataFinalDate = new Date(formatoDataParam.parse(dataFinal).getTime());
        } catch (ParseException e) {
            throw new ValidacaoException("Informe a data inicial e final!");
        }

        if(quantidade == null || quantidade.equals(""))
            throw new ValidacaoException("Informe a quantidade!");

        BigDecimal quantidadeBigDecimal = new BigDecimal(quantidade.replace(',', '.'));
        MoedaDAO moedaDAO = new MoedaDAO();
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        List<HistoricoMoeda> listaHistoricosMoeda = moedaDAO.selectHistoricoMoeda(
                idMoedaInt,
                new Date(new DateTime().toDate().getTime()),
                new Date(formatoData.parse("01/01/2012").getTime())
        );

        int qtdeDiasPeriodo = Days.daysBetween(new DateTime(dataInicialDate), new DateTime(dataFinalDate)).getDays();

        LocalDate dataInicialDateTime = new DateTime(dataInicialDate).toLocalDate();
        LocalDate dataFinalDateTime = new DateTime(dataFinalDate).toLocalDate();
        BigDecimal somatorioValorFechamento = new BigDecimal(0);

        for (HistoricoMoeda historicoMoeda : listaHistoricosMoeda) {
            LocalDate dataHistorico = new DateTime(historicoMoeda.getDataHistorico()).toLocalDate();
            if(dataHistorico.compareTo(dataInicialDateTime) >= 0 && dataHistorico.compareTo(dataFinalDateTime) <= 0)
                somatorioValorFechamento = somatorioValorFechamento.add(historicoMoeda.getValorFechamento());
        }
        BigDecimal valorMedioPeriodo = somatorioValorFechamento.divide(BigDecimal.valueOf(qtdeDiasPeriodo), 10, RoundingMode.HALF_EVEN);
        boolean aceitavelParaPeriodo = false;
        if(quantidadeBigDecimal.compareTo(valorMedioPeriodo) <= 0)
            aceitavelParaPeriodo = true;

        return new Simulacaoinvestimento(valorMedioPeriodo, aceitavelParaPeriodo);
    }

    private void validaDados(boolean validaDescricao, String idMoeda, String descricao, String dataInicial, String dataFinal, String quantidade) throws ValidacaoException {
        if(idMoeda == null)
            throw new ValidacaoException("Moeda não informada!");
        else if(validaDescricao && descricao == null)
            throw new ValidacaoException("Informe a descrição!");
        else if(dataInicial == null)
            throw new ValidacaoException("Informe a data inicial!");
        else if(dataFinal == null)
            throw new ValidacaoException("Informe a data final!");
        else if(quantidade == null)
            throw new ValidacaoException("Informe a quantidade!");
    }

}
