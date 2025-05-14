package mx.unam.aragon.repository;

import mx.unam.aragon.model.entity.RolEmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolEmpleadoRepository extends JpaRepository<RolEmpleadoEntity,Long> {
    Optional<RolEmpleadoEntity> findByRol(String rol);
}
