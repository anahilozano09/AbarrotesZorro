package mx.unam.aragon.service.RolEmpleado;

import mx.unam.aragon.model.entity.RolEmpleadoEntity;

import java.util.List;
import java.util.Optional;

public interface RolEmpleadoService {
    RolEmpleadoEntity save(RolEmpleadoEntity rolEmpleado);
    List<RolEmpleadoEntity> findAll();
    void deleteById(Long id);
    RolEmpleadoEntity findById(Long id);
    Optional<RolEmpleadoEntity> findByRol(String rol);
}
