package mx.unam.aragon.controller.empleado.admin;

import mx.unam.aragon.model.entity.CantidadProductoAlmacenEntity;
import mx.unam.aragon.model.entity.TipoProductoEntity;
import mx.unam.aragon.service.CantidadProductoAlmacen.CantidadProductoAlmacenService;
import mx.unam.aragon.service.TipoProducto.TipoProductoService;
import mx.unam.aragon.util.AlmacenExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/almacen")

public class AdminAlmacenController {

    @Autowired
    CantidadProductoAlmacenService cantidadProductoAlmacenService;
    @Autowired
    TipoProductoService tipoProductoService;
    @Autowired
    AlmacenExcel almacenExcel;

    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @GetMapping
    public String almacen(Model model){
        List<CantidadProductoAlmacenEntity> lista = cantidadProductoAlmacenService.findAll();
        List<TipoProductoEntity> listaTipoProducto = tipoProductoService.findAll();

        model.addAttribute("lista",lista);
        model.addAttribute("listaTipoProducto",listaTipoProducto);
        model.addAttribute("idTipoSeleccionado", 0);
        model.addAttribute("contenido","Productos en Almacen");
        return "admin/almacen/lista-almacen";
    }

    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @GetMapping("tipoProducto")
    public String almacenTipoProducto(Model model,@RequestParam Long idTipoProducto){
        List<CantidadProductoAlmacenEntity> lista;
        List<TipoProductoEntity> listaTipoProducto = tipoProductoService.findAll();

        if(idTipoProducto == 0){
            lista = cantidadProductoAlmacenService.findAll();
            model.addAttribute("contenido","Productos en Almacen");
        } else {
            lista = cantidadProductoAlmacenService.findByTipoProducto(idTipoProducto);
            TipoProductoEntity tipoProducto = tipoProductoService.findById(idTipoProducto);
            model.addAttribute("contenido","Productos en Almacen " + tipoProducto.getTipo());
        }

        model.addAttribute("lista",lista);
        model.addAttribute("listaTipoProducto",listaTipoProducto);
        model.addAttribute("idTipoSeleccionado", idTipoProducto);
        return "admin/almacen/lista-almacen";
    }

    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @GetMapping("exportar-excel")
    public ResponseEntity<byte[]> exportarExcel() throws IOException {
        List<CantidadProductoAlmacenEntity> productos = cantidadProductoAlmacenService.findAll();

        ByteArrayOutputStream outputStream = almacenExcel.exportarExcel(productos);
        byte[] bytes = outputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "inventario_almacen.xlsx");

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @GetMapping("/exportar-excel-tipoProducto")
    public ResponseEntity<byte[]> exportarExcelPorTipo(
            @RequestParam(name = "idTipoProducto", required = false) Long idTipoProducto) throws IOException {

        List<CantidadProductoAlmacenEntity> productos;
        String nombreArchivo;

        if (idTipoProducto != null && idTipoProducto != 0) {
            productos = cantidadProductoAlmacenService.findByTipoProducto(idTipoProducto);
            TipoProductoEntity tipo = tipoProductoService.findById(idTipoProducto);
            nombreArchivo = "inventario_" + tipo.getTipo().toLowerCase().replace(" ", "_") + ".xlsx";
        } else {
            productos = cantidadProductoAlmacenService.findAll();
            nombreArchivo = "inventario_almacen.xlsx";
        }

        ByteArrayOutputStream outputStream = almacenExcel.exportarExcel(productos);
        byte[] bytes = outputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", nombreArchivo);

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}
