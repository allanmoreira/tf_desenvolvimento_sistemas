package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import persistencia.modelos.Moeda;
import persistencia.modelos.PeriodoHistorico;
import persistencia.modelos.Usuario;
import regraNegocio.BDException;
import regraNegocio.InvestimentoControle;
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
public class InvestimentoServlet {

    @RequestMapping(value="novo_investimento", method = RequestMethod.POST)
    public void novoInvestimento(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<>();
        boolean isValid = false;
        String msgErro = null;

        String idMoeda = request.getParameter("id_moeda");
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        String descricao = request.getParameter("descricao");
        String dataInicial = request.getParameter("data_inicial");
        String dataFinal = request.getParameter("data_final");
        String quantidade = request.getParameter("quantidade");

        InvestimentoControle investimentoControle = new InvestimentoControle();

        try {
            investimentoControle.insereInvestimento(idMoeda, usuario, descricao, dataInicial, dataFinal, quantidade);
            isValid = true;
        } catch (BDException | ValidacaoException e) {
            msgErro = e.getMessage();
        }

        map.put("isValid", isValid);
        map.put("msgErro", msgErro);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        response.getWriter().write(gson.toJson(map));
    }

}
