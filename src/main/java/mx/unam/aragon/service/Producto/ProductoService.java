package mx.unam.aragon.service.Producto;

import mx.unam.aragon.model.entity.ProductoEntity;

import java.util.List;

public interface ProductoService {
    ProductoEntity save(ProductoEntity producto);
    List<ProductoEntity> findAll();
}
