package FabianCh.ProyectoFacturaComplexivo.controllers;


import FabianCh.ProyectoFacturaComplexivo.models.entity.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @RequestMapping (value="/home")
    public String IrHome(){
        return "home";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String InicioSesion(@ModelAttribute(name="loginForm")LoginForm loginForm, Model model){
        String usuario=loginForm.getUsuario();
        String password=loginForm.getPassword();
        if("admin".equals(usuario) && "12345".equals(password)){
             return "redirect:/cliente/listarhome";
        }else{
            model.addAttribute("cuentaInvalida",true);
            return "index";
        }
    }
    @RequestMapping(value = "/cerrar")
    public String cerrarSesion(){
        return "index";
    }


}
