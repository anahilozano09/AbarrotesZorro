package mx.unam.aragon.controller.cliente;

import jakarta.validation.Valid;
import mx.unam.aragon.model.entity.ClienteEntity;
import mx.unam.aragon.service.Cliente.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "cliente")
public class ClienteController {
    @Autowired
    ClienteService clienteService;


    @GetMapping("alta-cliente")
    public String altaCliente(Model model){
        ClienteEntity cliente=new ClienteEntity();
        model.addAttribute("cliente",cliente);
        model.addAttribute("contenido","Alta Cliente");
        return "cajero/alta-cliente";
    }

    @PostMapping("guardar-cliente")
    public String guardarCliente(@Valid @ModelAttribute(value = "cliente") ClienteEntity cliente,
                                 BindingResult result, Model model) {

        // Validar si ya existe un cliente con el mismo teléfono
        if (clienteService.existsByTelefono(cliente.getTelefono())) {
            result.rejectValue("telefono", "error.cliente", "Ya existe un cliente con ese número de teléfono.");
        }

        // Validar si ya existe un cliente con el mismo email
        if (clienteService.existsByEmail(cliente.getEmail())) {
            result.rejectValue("email", "error.cliente", "Ya existe un cliente con ese email.");
        }

        // Si hay errores de validación, se regresa al formulario
        if (result.hasErrors()) {
            model.addAttribute("contenido", "Alta Cliente");
            return "cajero/alta-cliente";
        }

        // Generar número de cuenta único de 12 dígitos



        // Guardar el cliente
        clienteService.save(cliente);
        model.addAttribute("nuevoNumCuenta", cliente.getNumCuenta());
        model.addAttribute("cliente", new ClienteEntity()); // limpia el formulario
        model.addAttribute("contenido", "Alta Cliente");
        return "cajero/alta-cliente";

    }



}
