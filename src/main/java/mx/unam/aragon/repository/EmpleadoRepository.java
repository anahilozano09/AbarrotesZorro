package mx.unam.aragon.repository;

import mx.unam.aragon.model.entity.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Long> {

    boolean existsByUsername(String username);
    @Query(value = "SELECT * FROM empleado WHERE BINARY username = :username", nativeQuery = true)
    Optional<EmpleadoEntity> findByUsername(@Param("username") String username);
}
