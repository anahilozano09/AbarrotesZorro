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
import mx.unam.aragon.service.CantidadProductoAlmacen.CantidadProductoAlmacenService;
import mx.unam.aragon.service.Cliente.ClienteService;
import mx.unam.aragon.service.CompraCliente.CompraClienteService;
import mx.unam.aragon.service.Empleado.EmpleadoService;
import mx.unam.aragon.service.HistoricoProductos.HistoricoProductosService;
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

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/cajero/compra-cliente")
public class CajeroCompraClienteController {

    @Autowired
    ClienteService clienteService;
    @Autowired
    TipoProductoService tipoProductoService;
    @Autowired
    ProductoService productoService;
    @Autowired
    CompraClienteService compraClienteService;
    @Autowired
    EmpleadoService empleadoService;
    @Autowired
    HistoricoProductosService historicoProductosService;
    @Autowired
    CantidadProductoAlmacenService cantidadProductoAlmacenService;



    @Value("${external.image.dir}")
    private String archivoRuta;

    @PreAuthorize("hasAuthority('ROLE_Cajero')")
    @GetMapping
    public String altaCompra(Model model){
        model.addAttribute("tipoProducto",tipoProductoService.findAll());
        model.addAttribute("productos", productoService.findAll());
        return "cajero/alta-compraCliente";
    }

    @PreAuthorize("hasAuthority('ROLE_Cajero')")
    @PostMapping("guardar-compra")
    public String guardarCompra(@RequestParam String valorBusqueda,
                                @RequestParam(required = false) Long productoId,
                                @RequestParam(required = false) Integer cantidad,
                                Model model, Principal principal) {

        // Buscar cliente por número de cuenta, email o teléfono
        Optional<ClienteEntity> clienteOpt = clienteService.findByNumCuenta(valorBusqueda);
        if (clienteOpt.isEmpty()) clienteOpt = clienteService.findByEmail(valorBusqueda);
        if (clienteOpt.isEmpty()) clienteOpt = clienteService.findByTelefono(valorBusqueda);

        if (clienteOpt.isEmpty()) {
            model.addAttribute("error", "No se encontró un cliente con el valor proporcionado.");
            model.addAttribute("tipoProducto", tipoProductoService.findAll());
            model.addAttribute("productos", productoService.findAll());
            return "cajero/alta-compraCliente";
        }

        ClienteEntity cliente = clienteOpt.get();

        try {
            // Obtener empleado que está logueado
            EmpleadoEntity empleado = empleadoService.findByUsername(principal.getName())
                    .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));

            // Obtener producto y validar existencia en inventario
            ProductoEntity producto = productoService.findById(productoId);
            CantidadProductoAlmacenEntity cantidadProductoAlmacen = cantidadProductoAlmacenService.findByProductoId(productoId);
            int cantidadActual = cantidadProductoAlmacen.getCantidad();

            if (cantidadActual < cantidad) {
                throw new IllegalArgumentException("No hay suficiente stock");
            }

            // Registrar compra del cliente
            CompraClienteEntity compraCliente = CompraClienteEntity.builder()
                    .cliente(cliente)
                    .empleado(empleado)
                    .fecha(LocalDate.now())
                    .total(producto.getPrecio() * cantidad)
                    .build();

            // Registrar en histórico y actualizar cantidad
            HistoricoProductosEntity historicoProducto = HistoricoProductosEntity.builder()
                    .compraCliente(compraCliente)
                    .cantidadProductoAlmacen(cantidadProductoAlmacen)
                    .cantidadAct(cantidadActual - cantidad)
                    .build();

            compraClienteService.save(compraCliente);
            historicoProductosService.save(historicoProducto);

            // Actualizar modelo con mensaje
            model.addAttribute("contenido", "Registro de Compra exitoso");

        } catch (Exception e) {
            model.addAttribute("error", "Registro de Compra fallido: " + e.getMessage());
        }

        // Cargar nuevamente la vista con tipos y productos
        model.addAttribute("tipoProducto", tipoProductoService.findAll());
        model.addAttribute("productos", productoService.findAll());
        model.addAttribute("cliente", clienteOpt.get()); // para mantenerlo en la vista si deseas
        return "cajero/alta-compraCliente";

    }


}