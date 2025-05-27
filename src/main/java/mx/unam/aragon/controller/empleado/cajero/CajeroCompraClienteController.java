package mx.unam.aragon.controller.empleado.cajero;

import jakarta.validation.Valid;
import mx.unam.aragon.model.entity.ClienteEntity;
import mx.unam.aragon.model.entity.CompraClienteEntity;
import mx.unam.aragon.model.entity.TipoProductoEntity;
import mx.unam.aragon.service.Cliente.ClienteService;
import mx.unam.aragon.service.CompraCliente.CompraClienteService;
import mx.unam.aragon.service.Producto.ProductoService;
import mx.unam.aragon.service.TipoProducto.TipoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "alta-compraCliente")
public class CajeroCompraClienteController {

    @Autowired
    CompraClienteService compraClienteService;

    @Autowired
    ClienteService clienteService;

    @Autowired
    TipoProductoService tipoProductoService;

    @Autowired
    ProductoService productoService;

    @PreAuthorize("hasAuthority('ROLE_Cajero')")
    @GetMapping
    public String altaCompra(Model model){
        CompraClienteEntity compra = new CompraClienteEntity();
        model.addAttribute("compra",compra);
        //Colocar una forma de buscar el cliente en el formulario
        model.addAttribute("cliente", clienteService.findAll());
        //Colocar una forma de buscar el tipo de producto en el formulario
        model.addAttribute("tipoProducto", tipoProductoService.findAll());
        //Colocar una forma de buscar el producto en el formulario dependiendo del tipo de producto
        model.addAttribute("producto", productoService.findAll());
        model.addAttribute("contenido","Alta Pago");
        return "cajero/alta-compraCliente";
    }

    @PreAuthorize("hasAuthority('ROLE_Cajero')")
    @PostMapping("guardar-compra")
    public String guardarCompra(@Valid @ModelAttribute(value = "compra") CompraClienteEntity compraCliente,
                                BindingResult result, Model model){
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.out.println("Error: " + error.getDefaultMessage());
            }
            return "cajero/alta-compraCliente";
        }
        //realizar la lògica de negocio
        compraClienteService.save(compraCliente);
        model.addAttribute("contenido","Se almaceno con exito");
        return "cajero/alta-compraCliente";
    }


    // Buscar cliente
    @GetMapping("/api/clientes/buscar")
    public ResponseEntity<Map<String, Object>> buscarClientePorNumCuenta(@RequestParam String numCuenta) {
        Optional<ClienteEntity> clienteOpt = clienteService.findByNumCuenta(numCuenta);

        return clienteOpt
                .map(cliente -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("id", cliente.getId());
                    data.put("nombre", cliente.getNombre());
                    return ResponseEntity.ok(data);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Cliente no encontrado")));
    }



}