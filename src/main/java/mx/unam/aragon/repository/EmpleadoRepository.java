package mx.unam.aragon.repository;

import mx.unam.aragon.model.entity.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity,Long> {
}
