package mx.unam.aragon.service.ProductoComprado;

import mx.unam.aragon.model.entity.ProductoCompradoEntity;

import java.util.List;

public interface ProductoCompradoService {
    ProductoCompradoEntity save(ProductoCompradoEntity productoComprado);
    List<ProductoCompradoEntity> findAll();
    void deleteById(Long id);
    ProductoCompradoEntity findById(Long id);
}
