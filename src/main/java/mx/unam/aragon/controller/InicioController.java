package mx.unam.aragon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class InicioController {

    @GetMapping("/")
    public String inicio(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

        model.addAttribute("contenido", "Bienvenido a Abarrotes el Zorro");
        return "inicio";
    }

    @GetMapping("/denegado")
    public String accesoDenegado() {
        return "denegado";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

}
