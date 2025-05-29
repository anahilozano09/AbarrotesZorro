package mx.unam.aragon.controller.empleado.admin;

import mx.unam.aragon.model.entity.*;
import mx.unam.aragon.service.CantidadProductoAlmacen.CantidadProductoAlmacenService;
import mx.unam.aragon.service.CompraCliente.CompraClienteService;
import mx.unam.aragon.service.HistoricoProductos.HistoricoProductosService;
import mx.unam.aragon.service.Producto.ProductoService;
import mx.unam.aragon.service.TipoProducto.TipoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/admin/historico")
public class AdminHistoricoController {

    @Autowired
    HistoricoProductosService historicoProductosService;
    @Autowired
    ProductoService productoService;
    @Autowired
    CantidadProductoAlmacenService cantidadProductoAlmacenService;
    @Autowired
    CompraClienteService compraClienteService;


    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @GetMapping
    public String listaHistorico(Model model){
        List<HistoricoProductosEntity> lista = historicoProductosService.findAll();

        model.addAttribute("lista",lista);
        model.addAttribute("contenido","Historico de productos en Almacen");
        return "admin/historico/lista-historico";
    }

    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @GetMapping("/fecha")
    public String historicoFecha(Model model,
                                 @RequestParam(required = false)
                                 @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha) {
        model.addAttribute("fechaActual", LocalDate.now());
        List<HistoricoProductosEntity> listaHistorico = historicoProductosService.findAll();

        if (fecha != null) {
            LocalDate hoy = LocalDate.now();
            if (fecha.isAfter(hoy)) {
                model.addAttribute("error", "No se puede seleccionar una fecha futura.");
            } else {
                listaHistorico = listaHistorico.stream()
                        .filter(h -> {
                            LocalDate fechaCompra = LocalDate.from(h.getCompraCliente().getFecha());
                            return fechaCompra.isEqual(fecha);
                        })
                        .collect(Collectors.toList());
            }
        }

        model.addAttribute("listaHistorico", procesamientoHistoricos(listaHistorico));
        model.addAttribute("contenido", "Histórico filtrado de productos en almacén");
        return "admin/historico/lista-historico";
    }

    private List<Map<String, Object>> procesamientoHistoricos(List<HistoricoProductosEntity> historicoProductos) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return historicoProductos.stream().map(h -> {
            Map<String, Object> datos = new HashMap<>();
            ProductoEntity producto = h.getCantidadProductoAlmacen().getProducto();
            CompraClienteEntity compra = h.getCompraCliente();
            int cantidadDespues = h.getCantidadAct();

            int cantidadRestada = compra.getDetalles().stream()
                    .filter(det -> det.getProducto().getId().equals(producto.getId()))
                    .mapToInt(DetalleCompraClienteEntity::getCantidad)
                    .sum();

            datos.put("id_historico", h.getId());
            datos.put("nombre_producto", producto.getNombre());
            datos.put("precio_producto", producto.getPrecio());
            datos.put("cantidad_despues", cantidadDespues);
            datos.put("cantidad_antes", cantidadDespues + cantidadRestada);
            datos.put("fecha_compra", compra.getFecha().format(formatter));
            return datos;
        }).collect(Collectors.toList());
    }



}
