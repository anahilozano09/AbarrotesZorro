package mx.unam.aragon.controller.empleado.administrador;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdministradorController {

    @PreAuthorize("hasAuthority('ROLE_Administrador')")// Asegura que solo usuarios con rol ADMINISTRADOR puedan acceder
    @GetMapping("/admin")
    public String administrador(Model model) {
        model.addAttribute("contenido", "¡Bienvenido, Administrador!");  // Mensaje dinámico
        return "admin/inicio";  // Retorna la plantilla admin/inicio.html
    }

}
