package mx.unam.aragon.service.Producto;

import mx.unam.aragon.model.entity.ProductoEntity;
import mx.unam.aragon.model.entity.TipoProductoEntity;

import java.util.List;

public interface ProductoService {
    ProductoEntity save(ProductoEntity producto);
    List<ProductoEntity> findAll();
    void deleteById(Long id);
    ProductoEntity findById(Long id);
    List<ProductoEntity> findByTipoProductoId(Long tipoProductoId);
    ProductoEntity findByTipoProducto(TipoProductoEntity tipoProducto);
}
