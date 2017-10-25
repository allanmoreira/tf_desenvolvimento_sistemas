package regraNegocio;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.springframework.web.client.RestTemplate;
import persistencia.dao.MoedaDAO;
import persistencia.modelos.DadosBitTrex;
import persistencia.modelos.HistoricoMoeda;
import persistencia.modelos.Moeda;
import java.lang.reflect.Type;
import java.sql.Date;
import java.util.ArrayList;
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

    public List<HistoricoMoeda> buscaHistoricoMoeda(String idMoeda) throws BDException, ValidacaoException {
        int idMoedaInt;
        if(idMoeda == null)
            throw new ValidacaoException("Informe uma moeda válida!");

        idMoedaInt = Integer.valueOf(idMoeda);
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

        return moedaDAO.selectHistoricoMoeda(idMoedaInt);

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
}
