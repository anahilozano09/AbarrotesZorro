package mx.unam.aragon.controller.empleado.admin;

import mx.unam.aragon.model.entity.CantidadProductoAlmacenEntity;
import mx.unam.aragon.model.entity.TipoProductoEntity;
import mx.unam.aragon.service.CantidadProductoAlmacen.CantidadProductoAlmacenService;
import mx.unam.aragon.service.TipoProducto.TipoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/admin/almacen")

public class AdminAlmacenController {
    @Autowired
    CantidadProductoAlmacenService cantidadProductoAlmacenService;
    @Autowired
    TipoProductoService tipoProductoService;

    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @GetMapping
    public String almacen(Model model){
        List<CantidadProductoAlmacenEntity> lista = cantidadProductoAlmacenService.findAll();
        List<TipoProductoEntity> listaTipoProducto = tipoProductoService.findAll();

        model.addAttribute("lista",lista);
        model.addAttribute("listaTipoProducto",listaTipoProducto);
        model.addAttribute("idTipoSeleccionado", 0);
        model.addAttribute("contenido","Productos en Almacen");
        return "admin/almacen/lista-almacen";
    }

    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @GetMapping("tipoProducto")
    public String almacenTipoProducto(Model model,@RequestParam Long idTipoProducto){
        List<CantidadProductoAlmacenEntity> lista;
        List<TipoProductoEntity> listaTipoProducto = tipoProductoService.findAll();

        if(idTipoProducto == 0){
            lista = cantidadProductoAlmacenService.findAll();
            model.addAttribute("contenido","Productos en Almacen");
        } else {
            lista = cantidadProductoAlmacenService.findByTipoProducto(idTipoProducto);
            TipoProductoEntity tipoProducto = tipoProductoService.findById(idTipoProducto);
            model.addAttribute("contenido","Productos en Almacen " + tipoProducto.getTipo());
        }

        model.addAttribute("lista",lista);
        model.addAttribute("listaTipoProducto",listaTipoProducto);
        return "admin/almacen/lista-almacen";
    }

}
