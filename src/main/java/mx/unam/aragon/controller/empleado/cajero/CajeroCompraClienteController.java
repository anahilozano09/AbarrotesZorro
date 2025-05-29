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

import java.math.BigDecimal;
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
                                @RequestParam(value = "clienteId", required = false) Long clienteId,
                                @RequestParam(value = "productos", required = false) List<String> productos,
                                Model model, Principal principal) {

        System.out.println("---- Inicia guardarCompra ----");
        System.out.println("valorBusqueda: " + valorBusqueda);
        System.out.println("Productos recibidos: " + productos);

        // Buscar cliente por valorBusqueda
        Optional<ClienteEntity> clienteOpt = clienteService.findByNumCuenta(valorBusqueda);
        if (clienteOpt.isEmpty()) clienteOpt = clienteService.findByEmail(valorBusqueda);
        if (clienteOpt.isEmpty()) clienteOpt = clienteService.findByTelefono(valorBusqueda);

        if (clienteOpt.isEmpty()) {
            System.out.println("No se encontró cliente con valorBusqueda: " + valorBusqueda);
            model.addAttribute("error", "No se encontró un cliente con el valor proporcionado.");
            model.addAttribute("tipoProducto", tipoProductoService.findAll());
            model.addAttribute("productos", productoService.findAll());
            return "cajero/alta-compraCliente";
        }

        ClienteEntity cliente = clienteOpt.get();
        System.out.println("Cliente encontrado: id=" + cliente.getId() + ", nombre=" + cliente.getNombre());

        if (productos == null || productos.isEmpty()) {
            model.addAttribute("error", "No se especificaron productos para la compra.");
            model.addAttribute("tipoProducto", tipoProductoService.findAll());
            model.addAttribute("productos", productoService.findAll());
            model.addAttribute("cliente", cliente);
            return "cajero/alta-compraCliente";
        }

        try {
            EmpleadoEntity empleado = empleadoService.findByUsername(principal.getName())
                    .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));

            System.out.println("Empleado logueado: id=" + empleado.getId() + ", username=" + empleado.getUsername());

            // Creamos compra con total 0 inicialmente, actualizaremos después
            CompraClienteEntity compraCliente = CompraClienteEntity.builder()
                    .cliente(cliente)
                    .empleado(empleado)
                    .fecha(LocalDate.now())
                    .total(0.0)
                    .build();

            compraClienteService.save(compraCliente); // Guardamos primero para obtener ID

            double totalCompra = 0.0;

            // Procesar cada producto y cantidad
            for (String prod : productos) {
                String[] partes = prod.split("-");
                if (partes.length != 2) {
                    throw new IllegalArgumentException("Formato de producto inválido: " + prod);
                }

                Long productoId = Long.parseLong(partes[0]);
                int cantidad = Integer.parseInt(partes[1]);

                ProductoEntity producto = productoService.findById(productoId);
                if (producto == null) {
                    throw new IllegalArgumentException("Producto no encontrado con ID: " + productoId);
                }

                CantidadProductoAlmacenEntity cantidadProductoAlmacen = cantidadProductoAlmacenService.findByProductoId(productoId);
                if (cantidadProductoAlmacen == null) {
                    throw new IllegalArgumentException("No se encontró el producto en el almacén con ID: " + productoId);
                }

                int cantidadActual = cantidadProductoAlmacen.getCantidad();
                System.out.println("Producto ID " + productoId + ": stock actual = " + cantidadActual);

                if (cantidadActual < cantidad) {
                    throw new IllegalArgumentException("No hay suficiente stock para el producto " + producto.getNombre());
                }

                // Actualizar stock
                cantidadProductoAlmacen.setCantidad(cantidadActual - cantidad);
                cantidadProductoAlmacenService.save(cantidadProductoAlmacen);
                System.out.println("Stock actualizado para producto ID " + productoId + ": " + cantidadProductoAlmacen.getCantidad());

                // Sumar al total de la compra
                totalCompra += producto.getPrecio() * cantidad;

                // Guardar histórico
                HistoricoProductosEntity historicoProducto = HistoricoProductosEntity.builder()
                        .compraCliente(compraCliente)
                        .cantidadProductoAlmacen(cantidadProductoAlmacen)
                        .cantidadAct(cantidadProductoAlmacen.getCantidad())
                        .build();

                historicoProductosService.save(historicoProducto);
                System.out.println("Histórico producto guardado para producto ID " + productoId);
            }

            // Actualizamos total de la compra y guardamos
            compraCliente.setTotal(totalCompra);
            compraClienteService.save(compraCliente);
            System.out.println("Compra cliente actualizada con total: " + totalCompra);

            model.addAttribute("contenido", "Registro de Compra exitoso");
        } catch (Exception e) {
            System.out.println("ERROR en guardarCompra: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Registro de Compra fallido: " + e.getMessage());
        }

        model.addAttribute("tipoProducto", tipoProductoService.findAll());
        model.addAttribute("productos", productoService.findAll());
        model.addAttribute("cliente", cliente);

        System.out.println("---- Fin guardarCompra ----");
        return "cajero/alta-compraCliente";
    }


}