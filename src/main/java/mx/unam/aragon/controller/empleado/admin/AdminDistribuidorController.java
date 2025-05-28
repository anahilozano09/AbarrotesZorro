package mx.unam.aragon.controller.empleado.admin;

import mx.unam.aragon.model.entity.PedidoProveedorEntity;
import mx.unam.aragon.model.entity.ProductoEntity;
import mx.unam.aragon.model.entity.ProveedorEntity;
import mx.unam.aragon.model.entity.TipoProductoEntity;
import mx.unam.aragon.service.CantidadProductoAlmacen.CantidadProductoAlmacenService;
import mx.unam.aragon.service.PedidoProveedor.PedidoProveedorService;
import mx.unam.aragon.service.Producto.ProductoService;
import mx.unam.aragon.service.TipoProducto.TipoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin/pedido")
public class AdminDistribuidorController {
    @Autowired
    PedidoProveedorService pedidoProveedorService;
    @Autowired
    TipoProductoService tipoProductoService;
    @Autowired
    ProductoService productoService;



    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @GetMapping
    public String pedidosProveedor(Model model) {
        List<PedidoProveedorEntity> lista = pedidoProveedorService.findAll();
        model.addAttribute("lista",lista);
        model.addAttribute("contenido","Pedidos a proveedor");
        return "admin/pedido/lista-pedido";
    }

    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @GetMapping("alta-pedido")
    public String altaPedido(Model model, @RequestParam(required = false) Long tipo){
        model.addAttribute("listaTipoProducto",tipoProductoService.findAll());

        if(tipo != null){
            model.addAttribute("listaProducto",productoService.findByTipoProductoId(tipo));
            model.addAttribute("idTipoProductoSeleccionado",tipo);
        } else {
            model.addAttribute("listaProducto",Collections.emptyList());
        }

        return "admin/pedido/alta-pedido";
    }

    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @GetMapping("productos-por-tipo")
    @ResponseBody
    public List<ProductoEntity> getProductosByTipoProducto(@RequestParam Long idTipoProducto) {
        System.out.println(">>> SOLICITUD RECIBIDA - Tipo ID: " + idTipoProducto);
        List<ProductoEntity> productos = productoService.findByTipoProductoId(idTipoProducto);
        System.out.println(">>> PRODUCTOS ENCONTRADOS: " + productos.size());
        return productos;
    }

    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @GetMapping("provedoor-por-producto")
    @ResponseBody
    public ProveedorEntity getProveedorByProducto(@RequestParam Long idProducto) {
        return productoService.findById(idProducto).getProveedor();
    }

    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @PostMapping("guardar-pedido")
    public String guardarPedido(@RequestParam Long idProducto,
                                @RequestParam Integer cantidad,
                                RedirectAttributes redirectAttributes){

        if(cantidad == null || cantidad < 1) {
            redirectAttributes.addFlashAttribute("error", "La cantidad debe ser mayor a 0");
            return "redirect:/admin/pedido/alta-pedido";
        }

        ProductoEntity producto = productoService.findById(idProducto);
        PedidoProveedorEntity pedido = PedidoProveedorEntity.builder()
                .cantidad(cantidad)
                .producto(producto)
                .build();

        pedidoProveedorService.save(pedido);

        redirectAttributes.addFlashAttribute("mensaje", "Pedido solicitado correctamente");

        return "redirect:/admin/pedido/alta-pedido";
    }


}
