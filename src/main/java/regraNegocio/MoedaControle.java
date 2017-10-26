package regraNegocio;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.joda.time.DateTime;
import org.springframework.web.client.RestTemplate;
import persistencia.dao.MoedaDAO;
import persistencia.modelos.DadosBitTrex;
import persistencia.modelos.HistoricoMoeda;
import persistencia.modelos.Moeda;
import persistencia.modelos.PeriodoHistorico;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    public List<PeriodoHistorico> buscaHistoricoMoeda(String idMoeda, String periodo, String limite) throws BDException, ValidacaoException {
        int idMoedaInt;
        int limiteInt;
        if(idMoeda == null)
            throw new ValidacaoException("Informe uma moeda!");
        if(periodo == null)
            throw new ValidacaoException("Informe um período!");
        if(limite == null)
            throw new ValidacaoException("Informe um limite!");

        idMoedaInt = Integer.valueOf(idMoeda);
        limiteInt = Integer.valueOf(limite);

        int diasSubtrair;
        int qtdeDiasLimite;
        switch (periodo) {
            case "s":
                qtdeDiasLimite = 7;
                break;
            case "m":
                qtdeDiasLimite = 30;
                break;
            case "a":
                qtdeDiasLimite = 365;
                break;
            default:
                throw new ValidacaoException("Informe um período válido!");
        }

        diasSubtrair = limiteInt * qtdeDiasLimite;
        java.util.Date dataHoje = new DateTime().toDate();
        DateTime dataHojeDateTime = new DateTime(dataHoje);
        Date dataLimite = new Date(dataHojeDateTime.minusDays(diasSubtrair).toDate().getTime());

        List<Moeda> listaMoeda = moedaDAO.selectAll(idMoedaInt);

        if(listaMoeda.size() == 0) {
            throw new ValidacaoException("Moeda não encontrada!");
        } else {
            ArrayList<DadosBitTrex> listaDadosBitTrex;
            Date ultimaDataHistoricoMoeda = moedaDAO.ultimaDataHistoricoMoeda(idMoedaInt);
            listaDadosBitTrex = atualizaHistoricoMoeda(listaMoeda.get(0).getSigla(), ultimaDataHistoricoMoeda);
            if(listaDadosBitTrex.size() > 0)
                moedaDAO.insert(idMoedaInt, listaDadosBitTrex);
        }

        List<HistoricoMoeda> listaHistoricoMoedas = moedaDAO.selectHistoricoMoeda(idMoedaInt, new Date(dataHoje.getTime()), dataLimite);
        List<PeriodoHistorico> listaPeriodosHistorico = new ArrayList<>();

        int cont = 0;
        String descricaoPeriodo = "";
        BigDecimal valorFechamento = new BigDecimal(0);
        BigDecimal valorVolume = new BigDecimal(0);
        PeriodoHistorico periodoHistorico;
        SimpleDateFormat formatoDataSQL = new SimpleDateFormat("dd/MM/yyyy");

        for (HistoricoMoeda historicoMoeda : listaHistoricoMoedas) {
            if(cont==0){
                if(periodo.equals("s")){
                    descricaoPeriodo = formatoDataSQL.format(historicoMoeda.getDataHistorico());
                } else if(periodo.equals("m")){
                    descricaoPeriodo = getMesDaData(historicoMoeda.getDataHistorico());
                } else {
                    descricaoPeriodo = String.valueOf(getAnoDaData(historicoMoeda.getDataHistorico()));
                }
            }

            valorFechamento = valorFechamento.add(historicoMoeda.getValorFechamento());
            valorVolume = valorVolume.add(historicoMoeda.getVolumeBTC());

            if(cont == qtdeDiasLimite-1){
                valorFechamento = valorFechamento.divide(new BigDecimal(qtdeDiasLimite), 10, RoundingMode.HALF_EVEN);
                valorVolume = valorVolume.divide(new BigDecimal(qtdeDiasLimite), 10, RoundingMode.HALF_EVEN);

                if(periodo.equals("s")){
                    descricaoPeriodo = descricaoPeriodo + " a " + formatoDataSQL.format(historicoMoeda.getDataHistorico());
                }

                periodoHistorico = new PeriodoHistorico(descricaoPeriodo, valorFechamento, valorVolume);
                listaPeriodosHistorico.add(periodoHistorico);
                descricaoPeriodo = "";
                valorFechamento = new BigDecimal(0);
                cont=0;
            } else {
                cont++;
            }
        }

        return listaPeriodosHistorico;

    }

    private ArrayList<DadosBitTrex> atualizaHistoricoMoeda(String siglaMoeda, Date ultimaDataHistoricoMoeda) {
        ArrayList<DadosBitTrex> listaDadosBitTrex = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();

        String respostaServidor = restTemplate.getForObject(
                "https://bittrex.com/Api/v2.0/pub/market/GetTicks?marketName=BTC-"+siglaMoeda+"&tickInterval=day&_=1507677509277",
                String.class
        );
        JsonObject jsonObject = new Gson().fromJson(respostaServidor, JsonObject.class);
        JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonElement jsonElement = jsonArray.get(i);
            DadosBitTrex dadosBitTrex = new Gson().fromJson(jsonElement, DadosBitTrex.class);
            if(ultimaDataHistoricoMoeda == null || dadosBitTrex.getT().compareTo(ultimaDataHistoricoMoeda) > 0)
                listaDadosBitTrex.add(dadosBitTrex);
        }

        return listaDadosBitTrex;
    }

    private String getMesDaData (Date dataSql) {
        Locale local = new Locale("pt","BR");
        DateTime novaData = new DateTime(dataSql);
        return novaData.monthOfYear().getAsText(local);
    }

    private int getAnoDaData (Date dataSql) {
        Locale local = new Locale("pt","BR");
        DateTime novaData = new DateTime(dataSql);
        return Integer.valueOf(novaData.yearOfEra().getAsText(local));
    }
}
