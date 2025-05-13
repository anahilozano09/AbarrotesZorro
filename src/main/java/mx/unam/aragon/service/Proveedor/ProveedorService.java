package mx.unam.aragon.service.Proveedor;

import mx.unam.aragon.model.entity.ProveedorEntity;

import java.util.List;

public interface ProveedorService {
    ProveedorEntity save(ProveedorEntity proveedor);
    List<ProveedorEntity> findAll();
}
