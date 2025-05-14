package mx.unam.aragon.controller.empleado.cajero;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CajeroController {

    @PreAuthorize("hasAuthority('ROLE_Cajero')")// Asegura que solo usuarios con rol ADMINISTRADOR puedan acceder
    @GetMapping("/cajero")
    public String cajero(Model model) {
        model.addAttribute("contenido", "¡Bienvenido, Cajero!");  // Mensaje dinámico
        return "cajero/inicio";  // Retorna la plantilla cajero/inicio.html
    }
}