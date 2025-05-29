package mx.unam.aragon.service.DetalleCompraCliente;

import mx.unam.aragon.model.entity.DetalleCompraClienteEntity;

import java.util.List;

public interface DetalleCompraClienteService {
    DetalleCompraClienteEntity save(DetalleCompraClienteEntity detalleCompraCliente);
    List<DetalleCompraClienteEntity> findAll();
    void deleteById(Long id);
    DetalleCompraClienteEntity findById(Long id);
}
