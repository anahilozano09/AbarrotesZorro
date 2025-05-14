package mx.unam.aragon.service.Empleado;

import mx.unam.aragon.model.entity.EmpleadoEntity;

import java.util.List;
import java.util.Optional;

public interface EmpleadoService {
    EmpleadoEntity save(EmpleadoEntity empleado);
    List<EmpleadoEntity> findAll();
    void deleteById(Long id);
    EmpleadoEntity findById(Long id);
    boolean existsByUsername(String username);
    Optional<EmpleadoEntity> findByUsername(String usuario);

}
