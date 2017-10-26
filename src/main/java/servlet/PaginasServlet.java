package servlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 *
 * @author allan
 */
@Controller
public class PaginasServlet {

    @RequestMapping("home")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home");
        return mv;
    }

    @RequestMapping("login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }

    @RequestMapping(value="logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:login";
    }

    @RequestMapping("registro")
    public ModelAndView registro() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("registro");
        return mv;
    }

    @RequestMapping("consulta_moeda")
    public ModelAndView consultaMoeda() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("consulta_moeda");
        return mv;
    }

    @RequestMapping("investimentos")
    public ModelAndView investimentos() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("investimentos");
        return mv;
    }


}
