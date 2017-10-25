package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import persistencia.modelos.Usuario;
import regraNegocio.BDException;
import regraNegocio.LoginControle;
import regraNegocio.ValidacaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginServlet {

    @RequestMapping(value="registrar_usuario", method = RequestMethod.POST)
    public void registrarUsuario(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<>();
        boolean isValid = false;
        String msgErro = null;

        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        LoginControle loginControle = new LoginControle();

        try {
            int idUsuario = loginControle.insereUsuario(nome, email, senha);
            Usuario usuario = new Usuario(idUsuario, nome, email, null);
            session.setAttribute("usuarioLogado", usuario);
            isValid = true;
        } catch (ValidacaoException | BDException e) {
            msgErro = e.getMessage();
        }

        map.put("isValid", isValid);
        map.put("msgErro", msgErro);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        response.getWriter().write(gson.toJson(map));
    }

    @RequestMapping(value="efetuar_login", method = RequestMethod.POST)
    public void efetuarLogin(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<>();
        boolean isValid = false;
        String msgErro = null;

        String login = request.getParameter("email");
        String senha = request.getParameter("senha");

        try {
            LoginControle loginControle = new LoginControle();
            Usuario usuario = loginControle.selecionaPorEmailSenha(login, senha);
            if(usuario != null) {
                session.setAttribute("usuarioLogado", usuario);
                isValid = true;
            } else {
                msgErro = "Usuário e/ou senha inválidos!";
            }

        } catch (ValidacaoException | BDException e) {
            msgErro = e.getMessage();
        }

        map.put("isValid", isValid);
        map.put("msgErro", msgErro);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(map));
    }

}
