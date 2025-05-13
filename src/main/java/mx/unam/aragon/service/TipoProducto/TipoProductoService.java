package mx.unam.aragon.service.TipoProducto;

import mx.unam.aragon.model.entity.TipoProductoEntity;

import java.util.List;

public interface TipoProductoService {
    TipoProductoEntity save(TipoProductoEntity tipoProducto);
    List<TipoProductoEntity> findAll();
}
