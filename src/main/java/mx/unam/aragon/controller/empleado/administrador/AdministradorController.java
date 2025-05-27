package mx.unam.aragon.controller.empleado.administrador;
import mx.unam.aragon.model.entity.CantidadProductoAlmacenEntity;
import mx.unam.aragon.model.entity.PedidoProveedorEntity;
import mx.unam.aragon.model.entity.TipoProductoEntity;
import mx.unam.aragon.service.CantidadProductoAlmacen.CantidadProductoAlmacenService;
import mx.unam.aragon.service.PedidoProveedor.PedidoProveedorService;
import mx.unam.aragon.service.Producto.ProductoService;
import mx.unam.aragon.service.TipoProducto.TipoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdministradorController {

    @Autowired
    CantidadProductoAlmacenService cantidadProductoAlmacenService;
    @Autowired
    TipoProductoService tipoProductoService;


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

    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @GetMapping("almacen/tipoProducto")
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


//    @PreAuthorize("hasAuthority('ROLE_Administrador')")
//    @GetMapping("pedidos")
//    public String pedidos(Model model){
//        List<PedidoProveedorEntity> lista = pedidoProveedorService.findAll();
//        model.addAttribute("lista",lista);
//        model.addAttribute("contenido","Pedidos a proveedor");
//        return "admin/almacen/pedido-proveedor";
//    }

}
