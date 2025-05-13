package mx.unam.aragon.repository;

import mx.unam.aragon.model.entity.TipoProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoProductoRepository extends JpaRepository<TipoProductoEntity,Long> {
}
