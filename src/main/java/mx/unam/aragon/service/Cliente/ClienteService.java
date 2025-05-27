package mx.unam.aragon.service.Cliente;

import mx.unam.aragon.model.entity.ClienteEntity;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    boolean existsByTelefono(String telefono);
    boolean existsByEmail(String email);
    boolean existsByNumCuenta(String numCuenta);
    ClienteEntity save(ClienteEntity cliente);
    List<ClienteEntity> findAll();
    void deleteById(Long id);
    ClienteEntity findById(Long id);
    Optional<ClienteEntity> findByNumCuenta(String numCuenta);
    Optional<ClienteEntity> findByEmail(String email);
    Optional<ClienteEntity> findByTelefono(String telefono);



}

