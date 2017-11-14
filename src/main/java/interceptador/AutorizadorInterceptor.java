package interceptador;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AutorizadorInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object controller) throws Exception {
				
		String uri = request.getRequestURI();
		if (uri.endsWith("home") || uri.endsWith("login") || uri.endsWith("efetuar_login") || uri.endsWith("login_usuario_firebase") ||
				uri.endsWith("registro") || uri.endsWith("registrar_usuario") ||
				uri.contains("static")) {
			return true;
		}

		if (request.getSession().getAttribute("usuarioLogado") != null) {
			return true;
		}
		
		response.sendRedirect("login");
		return false;
	}
}