package mx.unam.aragon.controller.empleado.cajero;


import mx.unam.aragon.model.entity.ProductoEntity;
import mx.unam.aragon.service.Producto.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<ProductoEntity> getProductosByTipoProducto(@RequestParam Long tipoProductoId) {
        return productoService.findByTipoProductoId(tipoProductoId);
    }
}