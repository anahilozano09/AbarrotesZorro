package mx.unam.aragon.controller.cliente;

import jakarta.validation.Valid;
import mx.unam.aragon.model.entity.*;
import mx.unam.aragon.repository.CompraClienteRepository;
import mx.unam.aragon.service.Cliente.ClienteService;
import mx.unam.aragon.service.Empleado.EmpleadoService;
import mx.unam.aragon.service.Producto.ProductoService;
import mx.unam.aragon.service.ProductoComprado.ProductoCompradoService;
import mx.unam.aragon.service.TipoProducto.TipoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping(value = "compra_cliente")
public class CompraClienteController {

    @Autowired
    private CompraClienteRepository compraClienteRepository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private TipoProductoService tipoProductoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private ProductoCompradoService productoCompradoService;

    @GetMapping("alta-compraCliente")
    public String altaCompraCliente(Model model) {
        CompraClienteEntity compraCliente = new CompraClienteEntity();
        List<ProductoEntity> productos = productoService.findAll();
        List<TipoProductoEntity> tipos = tipoProductoService.findAll();
        List<ClienteEntity> clientes = clienteService.findAll();
        List<EmpleadoEntity> empleados = empleadoService.findAll();
        List<ProductoCompradoEntity> productoComprados = productoCompradoService.findAll();

        model.addAttribute("compraCliente", compraCliente);
        model.addAttribute("productos", productos);
        model.addAttribute("tipos", tipos);
        model.addAttribute("clientes", clientes);
        model.addAttribute("empleados", empleados);
        model.addAttribute("productoComprados", productoComprados);
        model.addAttribute("contenido", "Alta Compra Cliente");

        return "cajero/alta-compraCliente";
    }

    @PostMapping("guardar-compraCliente")
    public String guardarCompraCliente(@Valid @ModelAttribute("compraCliente") CompraClienteEntity compraCliente,
                                  BindingResult result,
                                  Model model) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.out.println("Error: " + error.getDefaultMessage());
            }

            model.addAttribute("productos", productoService.findAll());
            model.addAttribute("tipos", tipoProductoService.findAll());
            model.addAttribute("clientes", clienteService.findAll());
            model.addAttribute("empleados", empleadoService.findAll());
            model.addAttribute("productosComprados", productoCompradoService.findAll());
            return "cajero/alta-compraCliente";
        }

        compraClienteRepository.save(compraCliente);
        model.addAttribute("contenido", "Almacenado con éxito");
        model.addAttribute("productos", productoService.findAll());
        model.addAttribute("tipos", tipoProductoService.findAll());
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("empleados", empleadoService.findAll());
        model.addAttribute("productosComprados", productoCompradoService.findAll());
        return "cajero/alta-compraCliente";
    }
}