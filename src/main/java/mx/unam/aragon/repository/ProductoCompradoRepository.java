package mx.unam.aragon.repository;

import mx.unam.aragon.model.entity.ProductoCompradoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoCompradoRepository extends JpaRepository<ProductoCompradoEntity, Long> {
//    List<ProductoCompradoEntity> findByCompraId(Long compraId);
}