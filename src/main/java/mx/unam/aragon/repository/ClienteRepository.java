package mx.unam.aragon.repository;

import mx.unam.aragon.model.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<ClienteEntity,Long> {
    boolean existsByEmail(String email);
    boolean existsByTelefono(String telefono);
    boolean existsByNumCuenta(String numCuenta);
    Optional<ClienteEntity> findByNumCuenta(String numCuenta);
}

