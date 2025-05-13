package mx.unam.aragon.repository;

import mx.unam.aragon.model.entity.HistoricoProductosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoProductosRepository extends JpaRepository<HistoricoProductosEntity,Long> {
}
