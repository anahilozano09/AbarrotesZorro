package mx.unam.aragon.controller.empleado.cajero;

import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.servlet.http.HttpServletResponse;
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
import mx.unam.aragon.util.EmailService;
import mx.unam.aragon.util.PDFGenerador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/cajero/compra-cliente")
public class CajeroCompraClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private TipoProductoService tipoProductoService;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private CompraClienteService compraClienteService;
    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private HistoricoProductosService historicoProductosService;
    @Autowired
    private CantidadProductoAlmacenService cantidadProductoAlmacenService;
    @Autowired
    private PDFGenerador pdfGenerador;
    @Autowired
    private EmailService emailService;

    @Value("${external.image.dir}")
    private String archivoRuta;

    @PreAuthorize("hasAuthority('ROLE_Cajero')")
    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("tipoProducto", tipoProductoService.findAll());
        return "cajero/alta-compraCliente";
    }

    @PreAuthorize("hasAuthority('ROLE_Cajero')")
    @PostMapping("/buscar-cliente")
    public String buscarCliente(@RequestParam("valorBusqueda") String valorBusqueda, Model model) {
        Optional<ClienteEntity> clienteOpt = buscarClientePorCriterios(valorBusqueda);

        model.addAttribute("tipoProducto", tipoProductoService.findAll());

        if (clienteOpt.isEmpty()) {
            model.addAttribute("contenido", "Cliente no encontrado con ese dato.");
        } else {
            model.addAttribute("cliente", clienteOpt.get());
        }
        return "cajero/alta-compraCliente";
    }

    @PreAuthorize("hasAuthority('ROLE_Cajero')")
    @GetMapping("/productos-por-tipo/{tipoId}")
    @ResponseBody
    public List<ProductoEntity> obtenerProductosPorTipo(@PathVariable Long tipoId) {
        return productoService.findByTipoProductoId(tipoId);
    }

    @PreAuthorize("hasAuthority('ROLE_Cajero')")
    @PostMapping("/guardar-compra")
    public String guardarCompra(@RequestParam("valorBusqueda") String valorBusqueda,
                                @RequestParam(value = "productos", required = false) List<String> productos,
                                Model model, Principal principal,
                                RedirectAttributes redirectAttributes) {

        Optional<ClienteEntity> clienteOpt = buscarClientePorCriterios(valorBusqueda);

        if (clienteOpt.isEmpty()) {
            model.addAttribute("contenido", "Cliente no encontrado.");
            model.addAttribute("tipoProducto", tipoProductoService.findAll());
            return "cajero/alta-compraCliente";
        }

        ClienteEntity cliente = clienteOpt.get();

        if (productos == null || productos.isEmpty()) {
            model.addAttribute("cliente", cliente);
            model.addAttribute("tipoProducto", tipoProductoService.findAll());
            return "cajero/alta-compraCliente";
        }

        EmpleadoEntity empleado = empleadoService.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalStateException("Empleado no encontrado"));

        CompraClienteEntity compra = CompraClienteEntity.builder()
                .cliente(cliente)
                .empleado(empleado)
                .fecha(LocalDateTime.now())
                .total(0.0)
                .build();

        compraClienteService.save(compra);

        List<DetalleCompraClienteEntity> detalles = new ArrayList<>();
        double totalCompra = 0.0;

        for (String prodStr : productos) {
            String[] partes = prodStr.split("-");
            if (partes.length != 2) continue;

            Long productoId;
            int cantidad;
            try {
                productoId = Long.parseLong(partes[0]);
                cantidad = Integer.parseInt(partes[1]);
            } catch (NumberFormatException e) {
                continue;
            }

            ProductoEntity producto = productoService.findById(productoId);
            CantidadProductoAlmacenEntity stock = cantidadProductoAlmacenService.findByProductoId(productoId);

            if (producto == null || stock == null || stock.getCantidad() < cantidad) {
                model.addAttribute("contenido", "Producto no disponible o stock insuficiente: " + (producto != null ? producto.getNombre() : "ID " + productoId));
                model.addAttribute("cliente", cliente);
                model.addAttribute("tipoProducto", tipoProductoService.findAll());
                return "cajero/alta-compraCliente";
            }

            double subtotal = producto.getPrecio() * cantidad;
            totalCompra += subtotal;

            stock.setCantidad(stock.getCantidad() - cantidad);
            cantidadProductoAlmacenService.save(stock);

            DetalleCompraClienteEntity detalle = DetalleCompraClienteEntity.builder()
                    .compra(compra)
                    .producto(producto)
                    .cantidad(cantidad)
                    .build();
            detalles.add(detalle);

            HistoricoProductosEntity historico = HistoricoProductosEntity.builder()
                    .cantidadAct(stock.getCantidad())
                    .cantidadProductoAlmacen(stock)
                    .compraCliente(compra)
                    .build();
            historicoProductosService.save(historico);
        }

        compra.setTotal(totalCompra);
        compra.setDetalles(detalles);
        compraClienteService.save(compra);

        if (cliente.getEmail() != null && !cliente.getEmail().isEmpty()) {
            try {
                List<PDFGenerador.ProductoCompra> productosPDF = new ArrayList<>();
                for (DetalleCompraClienteEntity detalle : detalles) {
                    ProductoEntity p = detalle.getProducto();
                    productosPDF.add(new PDFGenerador.ProductoCompra(
                            p.getNombre(),
                            detalle.getCantidad(),
                            p.getPrecio(),
                            p.getImagen()
                    ));
                }

                java.io.File tempFile = java.io.File.createTempFile("factura_", ".pdf");
                try (java.io.FileOutputStream fos = new java.io.FileOutputStream(tempFile)) {
                    pdfGenerador.generarFactura(productosPDF, fos);
                }

                String asunto = "Factura de su compra - " + LocalDate.now();
                String totalFormateado = String.format("%.2f", totalCompra);

                String cuerpo = "Estimad@ " + cliente.getNombre() + ",\n\n"
                        + "Adjuntamos la factura de su compra realizada el "
                        + compra.getFecha().toLocalDate() + ".\n"
                        + "Total: $" + totalFormateado + "\n\n"
                        + "Gracias por su preferencia!";

                emailService.enviarCorreoConAdjunto(
                        cliente.getEmail(),
                        asunto,
                        cuerpo,
                        tempFile
                );

                tempFile.delete();

                redirectAttributes.addFlashAttribute("contenido", "Compra registrada y factura enviada al correo!");

            } catch (Exception e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("avisoCorreo",
                        "Compra exitosa, pero error al enviar correo: " + e.getMessage());
            }
        } else {
            redirectAttributes.addFlashAttribute("avisoCorreo",
                    "Compra exitosa, pero el cliente no tiene email registrado");
        }

        return "redirect:/cajero/compra-cliente";
    }

    private Optional<ClienteEntity> buscarClientePorCriterios(String valor) {
        Optional<ClienteEntity> clienteOpt = clienteService.findByNumCuenta(valor);
        if (clienteOpt.isEmpty()) clienteOpt = clienteService.findByEmail(valor);
        if (clienteOpt.isEmpty()) clienteOpt = clienteService.findByTelefono(valor);
        return clienteOpt;
    }

    @GetMapping("/cajero/compra-cliente/factura")
    public void generarFactura(HttpServletResponse response) throws Exception {
        List<PDFGenerador.ProductoCompra> productos = List.of(
                new PDFGenerador.ProductoCompra("Jabón", 2, 12.5, "jabon.jpg"),
                new PDFGenerador.ProductoCompra("Shampoo", 1, 30.0, "shampoo.jpg")
        );

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"factura.pdf\"");

        pdfGenerador.generarFactura(productos, response.getOutputStream());
    }
}