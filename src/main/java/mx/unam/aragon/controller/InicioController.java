package mx.unam.aragon.controller;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InicioController {

    @GetMapping("/")
    public String inicio(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model,
            Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                    return "paginas/admin/inicio";
                } else if (authority.getAuthority().equals("ROLE_CAJERO")) {
                    return "paginas/cajero/inicio";
                }
            }
        }

        model.addAttribute("contenido", "Bienvenido a Abarrotes el Zorro");
        return "inicio";
    }

    @GetMapping("/denegado")
    public String accesoDenegado() {
        return "denegado";
    }


}
