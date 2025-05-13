package mx.unam.aragon.service.Empleado;

import mx.unam.aragon.model.entity.EmpleadoEntity;

import java.util.List;

public interface EmpleadoService {
    EmpleadoEntity save(EmpleadoEntity empleado);
    List<EmpleadoEntity> findAll();
}
