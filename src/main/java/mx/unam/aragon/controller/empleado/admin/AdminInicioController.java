package mx.unam.aragon.controller.empleado.admin;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminInicioController {

    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @GetMapping
    public String administrador(Model model) {
        model.addAttribute("contenido", "¡Bienvenido, Administrador!");
        return "plantillas/inicio";
    }



}
