package mx.unam.aragon.controller.empleado.administrador;
import mx.unam.aragon.model.entity.CantidadProductoAlmacenEntity;
import mx.unam.aragon.service.CantidadProductoAlmacen.CantidadProductoAlmacenService;
import mx.unam.aragon.service.Producto.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdministradorController {

    @Autowired
    CantidadProductoAlmacenService cantidadProductoAlmacenService;

    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @GetMapping
    public String administrador(Model model) {
        model.addAttribute("contenido", "¡Bienvenido, Administrador!");
        return "plantillas/inicio";
    }

    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @GetMapping("almacen")
    public String almacen(Model model){
        List<CantidadProductoAlmacenEntity> lista = cantidadProductoAlmacenService.findAll();
        model.addAttribute("lista",lista);
        model.addAttribute("contenido","Productos en Almacen");
        return "admin/almacen/lista-almacen";
    }

}
