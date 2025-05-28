package mx.unam.aragon.service.PedidoProveedor;

import mx.unam.aragon.model.entity.PedidoProveedorEntity;

import java.util.List;

public interface PedidoProveedorService {
    PedidoProveedorEntity save(PedidoProveedorEntity pedidoProveedor);
    List<PedidoProveedorEntity> findAll();
    void deleteById(Long id);
    PedidoProveedorEntity findById(Long id);
    PedidoProveedorEntity findByProductoId(Long productoId);
}
