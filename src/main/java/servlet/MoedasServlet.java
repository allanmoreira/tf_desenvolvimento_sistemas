package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import persistencia.modelos.HistoricoMoeda;
import persistencia.modelos.Moeda;
import regraNegocio.BDException;
import regraNegocio.MoedaControle;
import regraNegocio.ValidacaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MoedasServlet {

    @RequestMapping(value="buscar_moedas", method = RequestMethod.POST)
    public void buscarMoedas(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<>();
        boolean isValid = false;
        String msgErro = null;
        List<Moeda> listaMoedas = null;

        String idMoeda = request.getParameter("id_moeda");
        MoedaControle moedaControle = new MoedaControle();

        try {
            listaMoedas = moedaControle.buscaMoedas(idMoeda);
            isValid = true;
        } catch (BDException e) {
            msgErro = e.getMessage();
        }

        map.put("isValid", isValid);
        map.put("msgErro", msgErro);
        map.put("listaMoedas", listaMoedas);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        response.getWriter().write(gson.toJson(map));
    }

    @RequestMapping(value="historico_moeda", method = RequestMethod.POST)
    public void historicoMoeda(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<>();
        boolean isValid = false;
        String msgErro = null;
        List<HistoricoMoeda> listaHistoricoMoeda = null;

        String idMoeda = request.getParameter("id_moeda");
        MoedaControle moedaControle = new MoedaControle();

        try {
            listaHistoricoMoeda = moedaControle.buscaHistoricoMoeda(idMoeda);
            isValid = true;
        } catch (ValidacaoException | BDException e) {
            msgErro = e.getMessage();
        }

        map.put("isValid", isValid);
        map.put("msgErro", msgErro);
        map.put("listaHistoricoMoeda", listaHistoricoMoeda);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        response.getWriter().write(gson.toJson(map));
    }

}
