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
    public String inicio(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated() &&
                !"anonymousUser".equals(authentication.getPrincipal())) {

            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_Administrador"));
            boolean isCajero = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_Cajero"));

            if (isAdmin) {
                return "redirect:/admin";
            } else if (isCajero) {
                return "redirect:/cajero";
            }
        }
        return "inicio";
    }

    @GetMapping("/denegado")
    public String accesoDenegado() {
        return "denegado";
    }


}
