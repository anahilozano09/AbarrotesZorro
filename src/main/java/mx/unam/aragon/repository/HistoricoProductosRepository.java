package mx.unam.aragon.repository;

import mx.unam.aragon.model.entity.HistoricoProductosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricoProductosRepository extends JpaRepository<HistoricoProductosEntity,Long> {
    List<HistoricoProductosEntity> findByCantidadProductoAlmacenId(Long cantidadProductoAlmacenId);
}
