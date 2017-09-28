package servlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping("lista")
    public ModelAndView lista() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("lista");
        return mv;
    }


}
