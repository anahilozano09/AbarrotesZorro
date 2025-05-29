package mx.unam.aragon.repository;

import mx.unam.aragon.model.entity.DetalleCompraClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleCompraClienteRepository extends JpaRepository<DetalleCompraClienteEntity, Long> {
}
