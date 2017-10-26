package regraNegocio;

import persistencia.dao.InvestimentoDAO;
import persistencia.modelos.Investimento;
import persistencia.modelos.Moeda;
import persistencia.modelos.Usuario;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InvestimentoControle {

    private InvestimentoDAO investimentoDAO;

    public InvestimentoControle() {
        this.investimentoDAO = new InvestimentoDAO();
    }

    public void insereInvestimento(String idMoeda, Usuario usuario, String descricao, String dataInicial, String dataFinal, String quantidade) throws ValidacaoException, BDException {
        if(idMoeda == null)
            throw new ValidacaoException("Moeda não informada!");
        else if(descricao == null)
            throw new ValidacaoException("Informe a descrição!");
        else if(dataInicial == null)
            throw new ValidacaoException("Informe a data inicial!");
        else if(dataFinal == null)
            throw new ValidacaoException("Informe a data final!");
        else if(quantidade == null)
            throw new ValidacaoException("Informe a quantidade!");

        int idMoedaInt = Integer.valueOf(idMoeda);
        SimpleDateFormat formatoDataParam = new SimpleDateFormat("dd/MM/yyyy");
        Date dataInicialDate;
        Date dataFinalDate;

        try {
            dataInicialDate = new Date(formatoDataParam.parse(dataInicial).getTime());
            dataFinalDate = new Date(formatoDataParam.parse(dataInicial).getTime());
        } catch (ParseException e) {
            throw new ValidacaoException("Data em formato inválido!");
        }

        BigDecimal quantidadeBigDecimal = new BigDecimal(quantidade.replace(',', '.'));

        Investimento investimento = new Investimento(new Moeda(idMoedaInt), usuario, descricao, dataInicialDate, dataFinalDate, quantidadeBigDecimal);
        investimentoDAO.insert(investimento);
    }

}
