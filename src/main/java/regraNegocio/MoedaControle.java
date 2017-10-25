package regra_negocio;

import com.google.gson.Gson;
import org.springframework.web.client.RestTemplate;
import persistencia.dao.MoedaDAO;
import persistencia.modelos.HistoricoMoeda;
import persistencia.modelos.Moeda;
import java.lang.reflect.Type;

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
        return moedaDAO.selectHistoricoMoeda(idMoedaInt);
    }

    public void atualizaHistoricoMoeda(int idMoeda) {
        RestTemplate restTemplate = new RestTemplate();
        String respostaServidor = restTemplate.getForObject("/teste_rest/", String.class);
        
        Type listType = new TypeToken<List<Pessoa>>() {}.getType();
        List<Pessoa> listaPessoas = new Gson().fromJson(respostaServidor, listType);
    }
}
