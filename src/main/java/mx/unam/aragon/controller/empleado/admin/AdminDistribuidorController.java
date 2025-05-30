package mx.unam.aragon.controller.empleado.admin;

import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import mx.unam.aragon.model.entity.PedidoProveedorEntity;
import mx.unam.aragon.model.entity.ProductoEntity;
import mx.unam.aragon.model.entity.ProveedorEntity;
import mx.unam.aragon.model.entity.TipoProductoEntity;
import mx.unam.aragon.service.CantidadProductoAlmacen.CantidadProductoAlmacenService;
import mx.unam.aragon.service.PedidoProveedor.PedidoProveedorService;
import mx.unam.aragon.service.Producto.ProductoService;
import mx.unam.aragon.service.TipoProducto.TipoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/admin/pedido")
public class AdminDistribuidorController {
    @Autowired
    PedidoProveedorService pedidoProveedorService;
    @Autowired
    TipoProductoService tipoProductoService;
    @Autowired
    ProductoService productoService;

    @Value("${external.image.dir}")
    private String archivoRuta;



    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @GetMapping
    public String pedidosProveedor(Model model) {
        List<PedidoProveedorEntity> lista = pedidoProveedorService.findAll();
        model.addAttribute("lista",lista);
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
        System.out.println("Id del tipo de producto: " + idTipoProducto);
        List<ProductoEntity> productos = productoService.findByTipoProductoId(idTipoProducto);
        System.out.println("Productos encontrados: " + productos.size());
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

            pedidoProveedorService.save(pedido);

            redirectAttributes.addFlashAttribute("contenido", "Pedido solicitado correctamente");

            return "redirect:/admin/pedido/enviar-correo?idPedido=" + pedido.getId();
        }

    }

    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @GetMapping("enviar-correo")
    public String correoDistribuidor(RedirectAttributes model, @RequestParam Long idPedido) {
        PedidoProveedorEntity pedido = pedidoProveedorService.findById(idPedido);
        ProductoEntity producto = pedido.getProducto();
        ProveedorEntity proveedor = producto.getProveedor();

        if(pedido.getCantidad()<=0){
            model.addFlashAttribute("errorCorreo", "El pedido debe de ser mayor a 0");
            return "redirect:/admin/pedido/alta-pedido";
        }
        else {
            String gmail = "distribuidorpruebaspring@gmail.com";
            String pswd = "xtgp kpqg yqar tlys";
            Properties p = System.getProperties();
            p.setProperty("mail.smtps.host", "smpt.gmail.com");
            p.setProperty("mail.smtps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            p.setProperty("mail.smtps.socketFactory.fallback", "false");
            p.setProperty("mail.smtp.port", "465");
            p.setProperty("mail.smtp.socketFactory.port", "465");
            p.setProperty("mail.smtps.auth", "true");
            p.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
            p.setProperty("mail.smtps.ssl.trust", "smtp.gmail.com");
            p.setProperty("mail.smtp.ssl.quitwait", "false");

            //construccion del mensaje
            String cadena= "Pedido al distribuidor " + proveedor.getNombre();
            cadena += "<h3>Producto: <p>" + producto.getNombre() + "</p></h3><br>" +
                    "<h3>Precio Unitario: <p>" +"$"+ producto.getPrecio() + "</p></h3><br>" +
                    "<h3>Cantidad Solicitada: <p>" + pedido.getCantidad() + "</p></h3><br>";

            try {
                Session session = Session.getInstance(p, null);
                MimeMessage message = new MimeMessage(session);

                MimeBodyPart texto = new MimeBodyPart();
                texto.setContent(cadena, "text/html;charset=utf-8");

                //adjuntar la imagen
                BodyPart adjunto = new MimeBodyPart();
                String r = archivoRuta +"/"+ producto.getImagen();
                adjunto.setDataHandler(new DataHandler(new FileDataSource(r)));
                adjunto.setFileName("producto.png");
                Multipart multiple = new MimeMultipart();
                multiple.addBodyPart(texto);
                multiple.addBodyPart(adjunto);

                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(proveedor.getEmail(), false));
                message.setSubject("Pedido del producto: " + producto.getNombre());
                message.setContent(multiple);
                message.setSentDate(new Date());


                Transport transport = (Transport) session.getTransport("smtps");
                transport.connect("smtp.gmail.com", gmail, pswd);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            model.addAttribute("contenido", "El correo se mando con éxito");
            return "/admin/pedido/alta-pedido";
        }

    }


}
