package mx.unam.aragon.repository;

import mx.unam.aragon.model.entity.CantidadProductoAlmacenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CantidadProductoAlmacenRepository extends JpaRepository<CantidadProductoAlmacenEntity,Long> {
    @Query("SELECT c FROM cantidad_producto_almacen c " +
            "JOIN c.producto p " +
            "WHERE p.tipoProducto.id = :idTipoProducto")
    List<CantidadProductoAlmacenEntity> findByTipoProducto(Long idTipoProducto);
}

