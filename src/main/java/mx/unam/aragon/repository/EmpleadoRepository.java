package mx.unam.aragon.repository;

import mx.unam.aragon.model.entity.EmpleadoEntity;
import mx.unam.aragon.model.entity.RolEmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity,Long> {
    boolean existsByUsername(String username);
    Optional<EmpleadoEntity> findByUsername(String username);
}
