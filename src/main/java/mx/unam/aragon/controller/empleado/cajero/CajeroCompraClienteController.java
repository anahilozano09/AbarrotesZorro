package mx.unam.aragon.controller.empleado.cajero;

import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.validation.Valid;
import mx.unam.aragon.model.entity.*;
import mx.unam.aragon.service.Cliente.ClienteService;
import mx.unam.aragon.service.CompraCliente.CompraClienteService;
import mx.unam.aragon.service.PedidoProveedor.PedidoProveedorService;
import mx.unam.aragon.service.Producto.ProductoService;
import mx.unam.aragon.service.TipoProducto.TipoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/admin/pedido")
public class CajeroCompraClienteController {
    @Autowired
    CompraClienteService compraClienteService;
    @Autowired
    TipoProductoService tipoProductoService;
    @Autowired
    ProductoService productoService;

    @Value("${external.image.dir}")
    private String archivoRuta;

    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @GetMapping("alta-compra")
    public String altaCompra(Model model, @RequestParam(required = false) Long tipo){
        model.addAttribute("listaTipoProducto",tipoProductoService.findAll());

        if(tipo != null){
            model.addAttribute("listaProducto",productoService.findByTipoProductoId(tipo));
            model.addAttribute("idTipoProductoSeleccionado",tipo);
        } else {
            model.addAttribute("listaProducto", Collections.emptyList());
        }
        model.addAttribute("contenido", "Pedido al proveedor");
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
        else {
            ProductoEntity producto = productoService.findById(idProducto);
            PedidoProveedorEntity pedido = PedidoProveedorEntity.builder()
                    .cantidad(cantidad)
                    .producto(producto)
                    .build();

            redirectAttributes.addFlashAttribute("mensaje", "Pedido solicitado correctamente");

            return "redirect:/admin/pedido/enviar-correo?idPedido=" + pedido.getId();
        }

    }

    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @GetMapping("enviar-correo")
    public String correoDistribuidor(RedirectAttributes model, @RequestParam Long idPedido) {
        return "";

    }


}