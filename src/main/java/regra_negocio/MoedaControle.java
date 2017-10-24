package regra_negocio;

import persistencia.dao.MoedaDAO;
import persistencia.modelos.Moeda;
import persistencia.modelos.PeriodoHistorico;

import java.util.List;

public class MoedaControle {
    private MoedaDAO moedaDAO;

    public MoedaControle() {
        this.moedaDAO = new MoedaDAO();
    }

    public List<Moeda> buscaMoedas(String idMoeda) throws BDException {
        int idMoedaInt = 0;
        if(idMoeda != null)
            idMoedaInt = Integer.valueOf(idMoeda);
        return moedaDAO.selectAll(idMoedaInt);
    }

    public List<Moeda> buscaHistoricoMoeda(String idMoeda, String periodo, String limite) throws BDException, ValidacaoException {
        int idMoedaInt = 0;
        int limiteInt = 0;
        PeriodoHistorico periodoHistorico;

        if(idMoeda == null)
            throw new ValidacaoException("Informe uma moeda válida!");
        if(limite == null || limite.equals("0"))
            throw new ValidacaoException("Informe um limite válido!");

        switch (periodo) {
            case "s":
                periodoHistorico = PeriodoHistorico.SEMANA;
                break;
            case "m":
                periodoHistorico = PeriodoHistorico.MES;
                break;
            case "a":
                periodoHistorico = PeriodoHistorico.ANO;
                break;
            default:
                throw new ValidacaoException("Informe um período válido!");
        }

        idMoedaInt = Integer.valueOf(idMoeda);
        limiteInt = Integer.valueOf(limite);

        return moedaDAO.selectHistoricoMoeda(idMoedaInt, periodoHistorico, limiteInt);
    }
}
