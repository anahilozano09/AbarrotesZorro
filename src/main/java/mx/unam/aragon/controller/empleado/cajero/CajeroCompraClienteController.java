package mx.unam.aragon.controller.empleado.cajero;

import mx.unam.aragon.model.entity.*;
import mx.unam.aragon.service.Cliente.ClienteService;
import mx.unam.aragon.service.CompraCliente.CompraClienteService;
import mx.unam.aragon.service.Empleado.EmpleadoService;
import mx.unam.aragon.service.PedidoProveedor.PedidoProveedorService;
import mx.unam.aragon.service.Producto.ProductoService;
import mx.unam.aragon.service.ProductoComprado.ProductoCompradoService;
import mx.unam.aragon.service.TipoProducto.TipoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping(value = "/cajero")
public class CajeroCompraClienteController {

    @Autowired
    CompraClienteService compraClienteService;

    @Autowired
    ClienteService clienteService;

    @Autowired
    TipoProductoService tipoProductoService;

    @Autowired
    ProductoService productoService;

    @Autowired
    EmpleadoService empleadoService;


    @Autowired
    ProductoCompradoService productoCompradoService;

    @Autowired
    PedidoProveedorService pedidoProveedorService;

    @PreAuthorize("hasAuthority('ROLE_Cajero')")
    @GetMapping("alta-compraCliente")
    public String altaPedido(Model model, @RequestParam(required = false) Long tipo){
        model.addAttribute("listaTipoProducto",tipoProductoService.findAll());

        if(tipo != null){
            model.addAttribute("listaProducto",productoService.findByTipoProductoId(tipo));
            model.addAttribute("idTipoProductoSeleccionado",tipo);
        } else {
            model.addAttribute("listaProducto",Collections.emptyList());
        }

        return "/cajero/alta-compraCliente";
    }

    @PreAuthorize("hasAuthority('ROLE_Cajero')")
    @GetMapping("productos-por-tipo")
    @ResponseBody
    public List<ProductoEntity> getProductosByTipoProducto(@RequestParam Long idTipoProducto) {
        System.out.println(">>> SOLICITUD RECIBIDA - Tipo ID: " + idTipoProducto);
        List<ProductoEntity> productos = productoService.findByTipoProductoId(idTipoProducto);
        System.out.println(">>> PRODUCTOS ENCONTRADOS: " + productos.size());
        return productos;
    }

    @PreAuthorize("hasAuthority('ROLE_Cajero')")
    @GetMapping("producto")
    @ResponseBody
    public Integer getCantidadByProductoComprado(@RequestParam Long idProductoComprado) {
        return productoCompradoService.findById(idProductoComprado).getCantidad();
    }

    @PreAuthorize("hasAuthority('ROLE_Cajero')")
    @PostMapping("guardar-pedido")
    public String guardarPedido(@RequestParam Long idProducto,
                                @RequestParam Integer cantidad,
                                RedirectAttributes redirectAttributes){

        if(cantidad == null || cantidad < 1) {
            redirectAttributes.addFlashAttribute("error", "La cantidad debe ser mayor a 0");
            return "redirect:/cajero/alta-compraCliente";
        }

        ProductoEntity producto = productoService.findById(idProducto);
        PedidoProveedorEntity pedido = PedidoProveedorEntity.builder()
                .cantidad(cantidad)
                .producto(producto)
                .build();

        pedidoProveedorService.save(pedido);

        redirectAttributes.addFlashAttribute("mensaje", "Pedido solicitado correctamente");

        return "redirect:/cajero/alta-compraCliente";
    }


}
