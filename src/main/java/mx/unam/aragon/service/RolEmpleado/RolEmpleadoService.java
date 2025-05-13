package mx.unam.aragon.service.RolEmpleado;

import mx.unam.aragon.model.entity.RolEmpleadoEntity;

import java.util.List;

public interface RolEmpleadoService {
    RolEmpleadoEntity save(RolEmpleadoEntity rolEmpleado);
    List<RolEmpleadoEntity> findAll();
}
