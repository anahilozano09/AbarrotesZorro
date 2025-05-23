package mx.unam.aragon.repository;

import mx.unam.aragon.model.entity.ProductoEntity;
import mx.unam.aragon.model.entity.TipoProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<ProductoEntity,Long> {
    ProductoEntity findByTipoProducto(TipoProductoEntity tipoProducto);
}
