package mx.unam.aragon.controller.empleado.cajero;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/cajero")
public class CajeroController {

    @PreAuthorize("hasAuthority('ROLE_Cajero')")
    @GetMapping
    public String cajero(Model model) {
        model.addAttribute("contenido", "¡Bienvenido, Cajero!");
        return "plantillas/inicio";
    }
}