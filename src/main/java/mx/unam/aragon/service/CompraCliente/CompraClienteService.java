package mx.unam.aragon.service.CompraCliente;

import mx.unam.aragon.model.entity.CompraClienteEntity;

import java.util.List;

public interface CompraClienteService {
    CompraClienteEntity save(CompraClienteEntity compraCliente);
    List<CompraClienteEntity> findAll();
    void deleteById(Long id);
    CompraClienteEntity findById(Long id);
}
